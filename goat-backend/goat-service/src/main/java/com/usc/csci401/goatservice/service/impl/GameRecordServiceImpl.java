package com.usc.csci401.goatservice.service.impl;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatdao.GameRecordRepository;
import com.usc.csci401.goatdao.model.GameRecord;
import com.usc.csci401.goatservice.service.GameRecordService;
import com.usc.csci401.goatservice.service.TokenMappingService;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRecordServiceImpl implements GameRecordService {

  private final GameRecordRepository gameRecordRepository;

  @Autowired
  TokenMappingService tokenMappingService;

  @Override
  public Integer addGameRecord(String gameName, Integer tournamentId, Integer round, Integer seed1, Integer seed2){
    Preconditions.checkArgument(Strings.isNotBlank(gameName), "gameName cannot be blank");
    GameRecord gameRecord = new GameRecord();
    gameRecord.setGamename(gameName);
    gameRecord.setTournamentid(tournamentId);
    gameRecord.setSeed1(seed1);
    gameRecord.setSeed2(seed2);
    return gameRecordRepository.save(gameRecord).getGameid();
}

  @Override
  @Transactional
  public void updateWinner(String username, Integer gameId){
      Preconditions.checkArgument(StringUtils.isNotBlank(username), "username cannot be null");
      Preconditions.checkArgument(gameId != null, "gameId cannot be null");
      gameRecordRepository.updateWinnder(username, gameId);
  }

  @Override
  public List<GameRecord> findTournamentGame(Integer id){
    Preconditions.checkArgument(id != null, "tournament id cannot be null");
    return gameRecordRepository.findAllByTournamentid(id);
  }

  @Override
  public GameRecord getRecord(Integer id, Integer seed1, Integer seed2){
    if(seed1 != null && seed2 != null){
      Integer temp = seed1;
      seed1 = Math.min(seed1, seed2);
      seed2 = Math.max(temp, seed2);
    }

    return gameRecordRepository.findByTournamentidAndSeed1AndSeed2(id, seed1, seed2);
  }
}
