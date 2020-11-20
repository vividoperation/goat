package com.usc.csci401.goatservice.service.impl;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatcommon.exception.GameException;
import com.usc.csci401.goatdao.GamePlayerRepository;
import com.usc.csci401.goatdao.model.GamePlayer;
import com.usc.csci401.goatservice.service.GamePlayerService;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GamePlayerServiceImpl implements GamePlayerService {
  @Autowired
  GamePlayerRepository repo;

  @Override
  public void addGamePlayer(Integer gameId, String username){
    Preconditions.checkArgument(gameId != null, "gameId cannot be null");
    Preconditions.checkArgument(StringUtils.isNotBlank(username), "username cannot be blank");

    GamePlayer gamePlayer = new GamePlayer();
    gamePlayer.setUsername(username);
    gamePlayer.setGameid(gameId);

    repo.save(gamePlayer);
  }

  @Override
  public List<GamePlayer> getGamePlayer(Integer gameId){
    Preconditions.checkArgument(gameId != null, "gameId cannot be null");
    List<GamePlayer> players = repo.findAllByGameid(gameId);
    if(players == null){
      throw new GameException("game cannot be found");
    }

    return players;
  }

}
