package com.usc.csci401.goatdao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gamerecords")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRecord {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer gameid;

  @NotNull
  private String gamename;

  private String winner;

  private Integer tournamentid;

  private Integer round;

  private Integer seed1;

  private Integer seed2;


}
