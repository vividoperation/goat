package com.usc.csci401.goatcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenMappingDTO {

  private Integer token;

  private String username;

  private Integer gameid;

}
