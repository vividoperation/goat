package com.usc.csci401.goatcommon.exception;

import com.usc.csci401.goatcommon.constant.ResponseActionConstant;

public class GameException extends AbstractCommonException{
  public GameException() {
    this(ResponseActionConstant.ALERT);
  }

  public GameException(int action) {
    this.action = action;
  }

  public GameException(String message) {
    this.message = message;
    this.action = ResponseActionConstant.ALERT;
  }

  public GameException(int code, String message) {
    this.message = message;
  }

  public GameException(String message, Throwable cause) {
    super(message, cause);
    this.action = ResponseActionConstant.ALERT;
  }

}
