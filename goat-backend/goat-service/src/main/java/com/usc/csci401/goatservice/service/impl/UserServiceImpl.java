package com.usc.csci401.goatservice.service.impl;

import com.usc.csci401.goatcommon.dto.UserProfileDTO;
import com.usc.csci401.goatcommon.exception.UserException;
import com.usc.csci401.goatcommon.util.BeanConvertUtils;
import com.usc.csci401.goatdao.UserRepository;
import com.usc.csci401.goatdao.model.User;
import com.usc.csci401.goatservice.param.UserCreateParam;
import com.usc.csci401.goatservice.param.UserUpdateParam;
import com.usc.csci401.goatservice.service.UserService;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Override
  public UserProfileDTO selectUser(String username) {
    Optional<User> user = userRepository.findByUsername(username);
    if(!user.isPresent()){
      throw new UserException("User does not exist");
    }

    return BeanConvertUtils.convert(user.get(), UserProfileDTO.class, true);
  }

  @Transactional
  @Override
  public void updateUser(@Validated UserUpdateParam param, String username){
    userRepository.updateById(username, param.getEmail(), param.getPhone());
  }

  @Override
  public String createUser(@Validated UserCreateParam param){
    User user = BeanConvertUtils.convert(param, User.class, true);
    if(user == null){
      throw new UserException("User info does not exist");
    }

    return userRepository.save(user).getUsername();
  }

  @Override
  public String getPassword(String username){
    Optional<User> user = userRepository.findByUsername(username);
    if(!user.isPresent()){
      throw new UserException("User does not exist");
    }

    return user.get().getPassword();
  }

}
