package com.usc.csci401.goatservice.param;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateParam {

  @NotNull(message = "username cannot be null")
  private String username;
  @NotNull(message = "password cannot be null")
  private String password;
  private long phone;
  @NotNull(message = "email cannot be null")
  private String email;
}
