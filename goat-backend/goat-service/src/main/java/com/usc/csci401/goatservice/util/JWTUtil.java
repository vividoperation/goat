package com.usc.csci401.goatservice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.usc.csci401.goatcommon.exception.UserException;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {

  private static final long EXPIRE_TIME = 15 * 60 * 1000;

  private static final String SECRET = "usccsci401goat";

  public static String createToken(String username) {
    try {
      String token = "";
      Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);

      token = JWT.create().withAudience(username)
          .withExpiresAt(date)
          .sign(Algorithm.HMAC256(SECRET));
      return token;
    }catch (JWTCreationException exception){
      log.error("Error creating token : {}", exception.getMessage());
      throw new UserException(exception.getMessage());
    }
  }

  public static String createGameToken(String name){
    try{
      String token = "";
      token = JWT.create().withAudience(name)
          .sign(Algorithm.HMAC256(SECRET));
      return token;
    }catch (JWTCreationException exception){
      log.error("Error creating token : {}", exception.getMessage());
      throw new UserException(exception.getMessage());
    }
  }

  public static boolean verifyToken(String token){
    try{
      JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
      verifier.verify(token);
      return true;
    }catch (JWTVerificationException exception){
      log.error("Error veritying token : {}", exception.getMessage());
      throw new UserException(exception.getMessage());
    }
  }
}
