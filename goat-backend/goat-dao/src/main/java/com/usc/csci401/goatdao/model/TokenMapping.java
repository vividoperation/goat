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
@Table(name = "tokenmappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenMapping {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Integer token;

  @NotNull
  private String username;

  @NotNull
  private Integer gameid;
}
