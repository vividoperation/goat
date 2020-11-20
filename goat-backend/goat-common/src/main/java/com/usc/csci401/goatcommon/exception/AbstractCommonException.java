package com.usc.csci401.goatcommon.exception;

import com.usc.csci401.goatcommon.constant.ResponseActionConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbstractCommonException extends RuntimeException {

  protected int action = ResponseActionConstant.NO_ACTION;

  protected String message;

  protected AbstractCommonException() {
  }

  protected AbstractCommonException(String message) {
    super(message);
    this.message = message;
  }

  public AbstractCommonException(String message, Throwable cause) {
    super(message, cause);
    this.message = message;
  }

}
