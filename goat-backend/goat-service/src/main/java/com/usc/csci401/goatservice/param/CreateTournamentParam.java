package com.usc.csci401.goatservice.param;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTournamentParam {

  @NotNull(message = "Tournament name cannot be null")
  private String tournamentname;

  @NotNull(message = "maxPlayer number cannot be null")
  private Integer maxplayers;

  @NotNull(message = "game name cannot be null")
  private String gamename;



}
