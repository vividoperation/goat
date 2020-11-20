package com.usc.csci401.goatcommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TournamentDTO {

  private Integer tournamentid;

  private Integer maxplayers;

  private Integer currentplayers;

  private String tournamentname;

  private String gamename;

}
