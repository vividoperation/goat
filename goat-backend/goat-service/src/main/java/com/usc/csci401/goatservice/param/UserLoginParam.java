package com.usc.csci401.goatservice.param;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginParam {

  @NotNull(message = "username cannot be null")
  String username;

  @NotNull(message = "password cannot be null")
  String password;

}
