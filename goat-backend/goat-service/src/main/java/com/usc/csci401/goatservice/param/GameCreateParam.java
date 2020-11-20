package com.usc.csci401.goatservice.param;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCreateParam {

  @NotNull(message = "game name cannot be null")
  private String name;

  @NotNull(message = "start game url cannot be null")
  private String startgameurl;

  @NotNull(message = "create game url cannot be null")
  private String creategameurl;

}
