package com.usc.csci401.goatweb.advice;

import com.usc.csci401.goatcommon.exception.AbstractCommonException;
import com.usc.csci401.goatcommon.exception.GameException;
import com.usc.csci401.goatcommon.exception.SystemException;
import com.usc.csci401.goatcommon.exception.TokenException;
import com.usc.csci401.goatcommon.exception.TournamentException;
import com.usc.csci401.goatcommon.exception.UserException;
import com.usc.csci401.goatcommon.http.HttpResult;
import com.usc.csci401.goatdao.model.Tournament;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {
      UserException.class,
      SystemException.class,
      GameException.class,
      TournamentException.class
  })
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpResult knownException(HttpServletRequest httpRequest, AbstractCommonException e) {
    log.error("ignore error, requestUri={}, queryString={}", httpRequest.getRequestURI(),
        httpRequest.getQueryString(), e);
    return HttpResult.error(e.getMessage());
  }

  @ExceptionHandler(TokenException.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public HttpResult TokenException(Exception e, HttpServletRequest request) {
    log.error("unknown error, requestUri={}, queryString={}", request.getRequestURI(),
        request.getQueryString(), e);
    return HttpResult.error(e.getMessage());
  }


  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public HttpResult unknownException(Exception e, HttpServletRequest request) {
    log.error("unknown error, requestUri={}, queryString={}", request.getRequestURI(),
        request.getQueryString(), e);
    return HttpResult.error("Exception occured");
  }

}
