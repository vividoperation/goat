package com.usc.csci401.goatweb;

import com.usc.csci401.goatweb.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public AuthInterceptor getAuthInterceptor() {
    return new AuthInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    String[] excludeUrls = {
        "/user/login",
        "/user/register",
        "/games/reportwinner",
        "/games/createGame",
        "/games/updateGame"
    };
    registry.addInterceptor(getAuthInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns(excludeUrls);
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }


}
