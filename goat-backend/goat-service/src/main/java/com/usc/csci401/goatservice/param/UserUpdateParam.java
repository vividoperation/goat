package com.usc.csci401.goatservice.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateParam {

  private String email;

  private long phone;

}
