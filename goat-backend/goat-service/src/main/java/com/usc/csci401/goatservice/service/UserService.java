package com.usc.csci401.goatservice.service;

import com.usc.csci401.goatcommon.dto.UserProfileDTO;
import com.usc.csci401.goatservice.param.UserCreateParam;
import com.usc.csci401.goatservice.param.UserUpdateParam;

public interface UserService {

  UserProfileDTO selectUser(String username);

  void updateUser(UserUpdateParam param, String username);

  String createUser(UserCreateParam param);

  String getPassword(String username);

}
