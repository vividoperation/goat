package com.usc.csci401.goatcommon.exception;

import com.usc.csci401.goatcommon.constant.ResponseActionConstant;

public class SystemException extends AbstractCommonException{
  public SystemException() {
    this(ResponseActionConstant.ALERT);
  }

  public SystemException(int action) {
    this.action = action;
  }

  public SystemException(String message) {
    this.message = message;
    this.action = ResponseActionConstant.ALERT;
  }

  public SystemException(int code, String message) {
    this.message = message;
  }

  public SystemException(String message, Throwable cause) {
    super(message, cause);
    this.action = ResponseActionConstant.ALERT;
  }
}
