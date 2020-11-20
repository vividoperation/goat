package com.usc.csci401.goatweb.controller;

import com.usc.csci401.goatcommon.context.goatContext;
import com.usc.csci401.goatcommon.dto.GameDTO;
import com.usc.csci401.goatcommon.http.HttpResult;
import com.usc.csci401.goatfacade.GameRecordFacade;
import com.usc.csci401.goatservice.param.GameCreateParam;
import com.usc.csci401.goatservice.param.GameUpdateParam;
import com.usc.csci401.goatservice.param.WinnerUpdateParam;
import com.usc.csci401.goatservice.service.GameService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  @Autowired
  private GameRecordFacade gameRecordFacade;

  @Autowired
  private GameService gameService;

  @GetMapping("/newgame")
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResult<String> startGame(@RequestParam String gameName) {
    String username = goatContext.get("user");
    return HttpResult.success(gameRecordFacade.addGameRecord(gameName, username, null, null));
  }

  @PostMapping("/reportwinner")
  public HttpResult<String> updateWinner(@RequestBody @Validated WinnerUpdateParam param){
    gameRecordFacade.updateGameWinner(param.getWinner());
    return HttpResult.success("update success");
  }

  @PostMapping("/createGame")
  public HttpResult<String> createGame(@RequestBody @Validated GameCreateParam param){
    return HttpResult.success(gameService.addGame(param));
  }

  @PostMapping("/updateGame")
  public HttpResult<String> updateGame(@RequestBody @Validated GameUpdateParam param){
    gameService.updateGame(param);
    return HttpResult.success("update success");
  }

  @GetMapping("/games")
  public HttpResult<List<GameDTO>> getAllGame(){
      return HttpResult.success(gameService.getAllGame());
  }


}
