package com.usc.csci401.goatcommon.exception;

import com.usc.csci401.goatcommon.constant.ResponseActionConstant;

public class TournamentException extends AbstractCommonException{

  public TournamentException() {
    this(ResponseActionConstant.ALERT);
  }

  public TournamentException(int action) {
    this.action = action;
  }

  public TournamentException(String message) {
    this.message = message;
    this.action = ResponseActionConstant.ALERT;
  }

  public TournamentException(int code, String message) {
    this.message = message;
  }

  public TournamentException(String message, Throwable cause) {
    super(message, cause);
    this.action = ResponseActionConstant.ALERT;
  }

}
