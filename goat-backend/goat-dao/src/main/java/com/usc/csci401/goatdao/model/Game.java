package com.usc.csci401.goatdao.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "games")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

  @Id
  @NotNull
  private String name;

  @NotNull
  private String startgameurl;

  @NotNull
  private String creategameurl;

}
