package com.usc.csci401.goatservice.service;

import com.usc.csci401.goatdao.model.Seeding;
import java.util.List;

public interface SeedingService {

  Integer createSeeding(Integer tournamentId, String username, Integer maxPlayer);

  Seeding getSeed(Integer tournamentId, String username);

  void updateRound(Seeding seed);

  String getUsername(Integer touramentId, Integer seeding);

  List<Seeding> getByUsername(String username);
}
