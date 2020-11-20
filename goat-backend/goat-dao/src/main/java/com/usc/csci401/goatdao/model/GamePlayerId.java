package com.usc.csci401.goatdao.model;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GamePlayerId implements Serializable {

  private Integer gameid;

  private String username;

}
