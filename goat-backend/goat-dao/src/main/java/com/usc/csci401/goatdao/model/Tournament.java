package com.usc.csci401.goatdao.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tournaments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer tournamentid;

  private Integer maxplayers;

  private Integer currentplayers;

  private String tournamentname;

  private String gamename;
 
}
