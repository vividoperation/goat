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
@Table(name = "seedings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seeding {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer seedingid;

  private Integer tournamentid;

  private Integer seed;

  private String username;

  private Integer round;

}
