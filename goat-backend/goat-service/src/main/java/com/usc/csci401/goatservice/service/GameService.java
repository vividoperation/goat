package com.usc.csci401.goatservice.service;


import com.usc.csci401.goatcommon.dto.GameDTO;
import com.usc.csci401.goatservice.param.GameCreateParam;
import com.usc.csci401.goatservice.param.GameUpdateParam;
import java.util.List;
import org.springframework.validation.annotation.Validated;

public interface GameService {

  String addGame(@Validated GameCreateParam param);

  GameDTO getGame(String gameName);

  void updateGame(@Validated GameUpdateParam param);

  List<GameDTO> getAllGame();

}
