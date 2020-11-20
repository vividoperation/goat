package com.usc.csci401.goatcommon.http;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpResult<T> implements Serializable {

  private T result;

  private String msg;

  public HttpResult(String msg, T result) {
    this.msg = msg;
    this.result = result;
  }

  public static HttpResult error(String message) {
    HttpResult httpResult = new HttpResult();
    httpResult.setMsg(message);
    return httpResult;
  }
  public static HttpResult newFailure(String msg) {
    return build(msg, null);
  }

  public static HttpResult success() {
    return new HttpResult();
  }

  public static <T> HttpResult<T> success(T obj) {
    HttpResult<T> httpResult = new HttpResult<>();
    httpResult.setResult(obj);
    return httpResult;
  }

  public static <T> HttpResult<T> build(String message, T obj) {
    HttpResult<T> httpResult = new HttpResult<>();
    httpResult.setMsg(message);
    httpResult.setResult(obj);
    return httpResult;
  }



}
