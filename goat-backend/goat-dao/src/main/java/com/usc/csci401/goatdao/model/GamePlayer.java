package com.usc.csci401.goatdao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gameplayers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GamePlayerId.class)
public class GamePlayer {

  @Id
  private Integer gameid;

  @Id
  private String username;

}
