package com.usc.csci401.goatservice.service;

import com.usc.csci401.goatdao.model.GameRecord;
import java.util.List;

public interface GameRecordService {

  public Integer addGameRecord(String gameName, Integer tournamentId, Integer round, Integer seed1, Integer seed2);

  void updateWinner(String username, Integer gameId);

  List<GameRecord> findTournamentGame(Integer id);

  GameRecord getRecord(Integer id, Integer seed1, Integer seed2);


}
