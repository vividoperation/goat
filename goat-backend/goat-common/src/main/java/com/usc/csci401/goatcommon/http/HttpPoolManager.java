package com.usc.csci401.goatcommon.http;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

@Slf4j
public class HttpPoolManager {

  private static CloseableHttpClient closeableHttpClient;

  private static HttpRequestRetryHandler retryHandler;

  private final static int maxConn = 300;

  private final static int defaultMaxPerRoute = 15;

  private final static int socketTimeout = 30000;

  private final static int connTimeout = 15000;

  private final static int retryCount = 1;

  private final static int keepAliveDuration = 10 * 1000;


  public static HttpClient getHttpClient() {
    if (null == closeableHttpClient) {
      init();
    }
    return closeableHttpClient;
  }


  private static synchronized void init() {
    if (closeableHttpClient != null) {
      return;
    }
    LayeredConnectionSocketFactory sslsf;
    try {
      KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
      SSLContext sslContext = SSLContexts.custom()
          .setProtocol("TLS")
          .loadTrustMaterial(trustStore, (TrustStrategy) (chain, authType) -> true)
          .build();
      sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    } catch (KeyStoreException | KeyManagementException | NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
        .register("http", new PlainConnectionSocketFactory())
        .register("https", sslsf)
        .build();

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(connTimeout)
        .setSocketTimeout(socketTimeout)
        .setConnectionRequestTimeout(connTimeout)
        .setCookieSpec(CookieSpecs.IGNORE_COOKIES)
        .build();

    ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {
      @Override
      public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
        long keepAlive = super.getKeepAliveDuration(httpResponse, httpContext);
        if (keepAlive == -1) {
          keepAlive = keepAliveDuration;
        }
        return keepAlive;
      }
    };

    PoolingHttpClientConnectionManager clientConnManager = new PoolingHttpClientConnectionManager(
        socketFactoryRegistry);
    clientConnManager.setMaxTotal(maxConn);
    clientConnManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
    clientConnManager.setDefaultSocketConfig(SocketConfig.custom()
        .setSoTimeout(socketTimeout)
        .build());
    if (null == retryHandler) {
      createRetryHandler();
    }

    closeableHttpClient = HttpClients.custom()
        .setConnectionManager(clientConnManager)
        .setDefaultRequestConfig(requestConfig)
        .setRetryHandler(retryHandler)
        .setKeepAliveStrategy(keepAliveStrategy)
        .build();
  }


  private static void createRetryHandler() {
    if (retryHandler != null) {
      return;
    }
    retryHandler = (iOException, retryTimes, httpContext) -> {
      if (retryTimes > retryCount) {
        return false;
      }
      if (iOException instanceof NoHttpResponseException) {
        return true;
      }
      if (iOException instanceof SSLHandshakeException) {
        return false;
      }
      if (iOException instanceof InterruptedIOException) {
        return true;
      }
      if (iOException instanceof UnknownHostException) {
        return false;
      }
      if (iOException instanceof ConnectException) {
        return false;
      }
      if (iOException instanceof SSLException) {
        return false;
      }
      HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
      HttpRequest request = clientContext.getRequest();

      return !(request instanceof HttpEntityEnclosingRequest);
    };
  }

}
