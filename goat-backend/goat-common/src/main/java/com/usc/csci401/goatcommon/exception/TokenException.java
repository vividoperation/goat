package com.usc.csci401.goatcommon.exception;

import com.usc.csci401.goatcommon.constant.ResponseActionConstant;

public class TokenException extends AbstractCommonException {

  public TokenException() {
    this(ResponseActionConstant.ALERT);
  }

  public TokenException(int action) {
    this.action = action;
  }

  public TokenException(String message) {
    this.message = message;
    this.action = ResponseActionConstant.ALERT;
  }

  public TokenException(int code, String message) {
    this.message = message;
  }

  public TokenException(String message, Throwable cause) {
    super(message, cause);
    this.action = ResponseActionConstant.ALERT;
  }
}
