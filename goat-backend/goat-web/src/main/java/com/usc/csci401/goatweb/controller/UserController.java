package com.usc.csci401.goatweb.controller;

import com.usc.csci401.goatcommon.dto.UserProfileDTO;
import com.usc.csci401.goatcommon.exception.UserException;
import com.usc.csci401.goatcommon.http.HttpResult;
import com.usc.csci401.goatservice.param.UserCreateParam;
import com.usc.csci401.goatservice.param.UserLoginParam;
import com.usc.csci401.goatservice.param.UserUpdateParam;
import com.usc.csci401.goatservice.service.impl.UserServiceImpl;
import com.usc.csci401.goatservice.util.JWTUtil;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserServiceImpl userService;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  @GetMapping("/profile/get")
  public HttpResult<UserProfileDTO> getUserProfile(@RequestParam String username) {
    return HttpResult.success(userService.selectUser(username));
  }

  @PostMapping("/profile/update")
  public HttpResult<Boolean> updateUserProfile(@RequestBody @Validated UserUpdateParam param, @RequestParam String username){
    userService.updateUser(param, username);
    return HttpResult.success(true);
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResult<String> registerUser(@RequestBody @Validated UserCreateParam param){
    param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
    return HttpResult.success(JWTUtil.createToken(userService.createUser(param)));
  }

  @PostMapping("/login")
  public HttpResult<String> login(@RequestBody @Validated UserLoginParam param){
    String password = userService.getPassword(param.getUsername());
    if(!bCryptPasswordEncoder.matches(param.getPassword(), password)){
      throw new UserException("Password is not correct, please retry");
    }

    String token = JWTUtil.createToken(param.getUsername());
    return HttpResult.success(token);
  }



}
