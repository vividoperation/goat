package com.usc.csci401.goatservice.service.impl;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatcommon.dto.GameDTO;
import com.usc.csci401.goatcommon.exception.GameException;
import com.usc.csci401.goatcommon.util.BeanConvertUtils;
import com.usc.csci401.goatdao.GameRepository;
import com.usc.csci401.goatdao.model.Game;
import com.usc.csci401.goatservice.param.GameCreateParam;
import com.usc.csci401.goatservice.param.GameUpdateParam;
import com.usc.csci401.goatservice.service.GameService;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

  private final GameRepository gameRepository;

  @Override
  public String addGame(@Validated GameCreateParam param){
    Game game = BeanConvertUtils.convert(param, Game.class, true);
    if(game == null){
      throw new GameException("game cannot be null");
    }

    return gameRepository.save(game).getName();
  }

  @Override
  public GameDTO getGame(String gameName){
    Preconditions.checkArgument(StringUtils.isNotBlank(gameName), "gameName cannot be blank");

    Optional<Game> game = gameRepository.findByName(gameName);
    if(!game.isPresent()){
      throw new GameException("Game does not exist");
    }

    return BeanConvertUtils.convert(game.get(), GameDTO.class, true);
  }

  @Override
  @Transactional
  public void updateGame(@Validated GameUpdateParam param){
    Game game = BeanConvertUtils.convert(param, Game.class, true);
    if(game == null){
      throw new GameException("game cannot be null");
    }

    Optional<Game> originGame = gameRepository.findByName(param.getName());
    if(!originGame.isPresent()){
      throw new GameException("Game does not exist");
    }

    gameRepository.updateById(param.getName(), param.getStartgameurl(), param.getCreategameurl());
  }

  @Override
  public List<GameDTO> getAllGame(){
    return BeanConvertUtils.convert(gameRepository.findAll(), GameDTO.class, true);
  }

}
