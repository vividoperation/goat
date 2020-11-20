package com.usc.csci401.goatweb.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.usc.csci401.goatcommon.constant.LogConstant;
import com.usc.csci401.goatcommon.context.goatContext;
import com.usc.csci401.goatcommon.dto.UserProfileDTO;
import com.usc.csci401.goatcommon.exception.TokenException;
import com.usc.csci401.goatcommon.exception.UserException;
import com.usc.csci401.goatservice.service.UserService;
import com.usc.csci401.goatservice.util.JWTUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AuthInterceptor implements HandlerInterceptor {

  @Autowired
  UserService userService;

  @Override
  public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
    String token = httpServletRequest.getHeader("token");
    httpServletRequest.setAttribute(LogConstant.REQ_START_TIME, System.currentTimeMillis());
     if (token == null) {
         throw new TokenException("Token not found, Please login");
     }

       String username;
        try {
          username = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
          throw new TokenException("Token decode error");
        }

        UserProfileDTO userProfile = userService.selectUser(username);
        if(userProfile == null) {
          throw new UserException("user does not exist, please signup or login");
        }

        boolean result = JWTUtil.verifyToken(token);
        if(result){
          goatContext.put("user", username);
        }

        return result;
  }

  @Override
  public void postHandle(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o, ModelAndView modelAndView) throws Exception {

  }

  @Override
  public void afterCompletion(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      Object o, Exception e) throws Exception {
  }

}
