package com.usc.csci401.goatservice.service;

import com.usc.csci401.goatdao.model.GamePlayer;
import java.util.List;

public interface GamePlayerService {

  void addGamePlayer(Integer gameId, String username);

  List<GamePlayer> getGamePlayer(Integer gameId);
}
