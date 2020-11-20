package com.usc.csci401.goatcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {

  private String name;

  private String startgameurl;

  private String creategameurl;
}
