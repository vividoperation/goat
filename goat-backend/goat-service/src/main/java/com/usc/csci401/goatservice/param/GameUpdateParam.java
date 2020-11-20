package com.usc.csci401.goatservice.param;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameUpdateParam {

  @NotNull(message = "game name cannot be null")
  private String name;

  private String startgameurl;

  private String creategameurl;
}
