package com.usc.csci401.goatcommon.exception;

import com.usc.csci401.goatcommon.constant.ResponseActionConstant;

public class UserException extends AbstractCommonException{

  public UserException() {
    this(ResponseActionConstant.ALERT);
  }

  public UserException(int action) {
    this.action = action;
  }

  public UserException(String message) {
    this.message = message;
    this.action = ResponseActionConstant.ALERT;
  }

  public UserException(int code, String message) {
    this.message = message;
  }

  public UserException(String message, Throwable cause) {
    super(message, cause);
    this.action = ResponseActionConstant.ALERT;
  }

}
