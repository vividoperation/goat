package com.usc.csci401.goatcommon.util;

import com.usc.csci401.goatcommon.http.HttpPoolManager;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

@Slf4j
public class HttpUtils {

  public static String doPost(String url, String content, HttpStatus statusCode) throws IOException {
    HttpClient client = HttpPoolManager.getHttpClient();
    HttpPost post = new HttpPost(url);
    StringEntity se = new StringEntity(content, "utf-8");
    se.setContentType("application/json");
    post.setEntity(se);
    HttpResponse response = client.execute(post);
    StatusLine sl = response.getStatusLine();
    HttpEntity entity = response.getEntity();
    String contentRep = EntityUtils.toString(entity, "utf-8");
    if (response.getStatusLine().getStatusCode() == statusCode.value()) {
      return contentRep;
    } else {
      log.warn("req url failed, url: {}, retcode: {}, content: {}", url, sl.getStatusCode(),
          contentRep);
    }
    return null;
  }
}
