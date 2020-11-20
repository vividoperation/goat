package com.usc.csci401.goatweb.controller;

import com.usc.csci401.goatcommon.context.goatContext;
import com.usc.csci401.goatcommon.dto.TournamentDTO;
import com.usc.csci401.goatcommon.http.HttpResult;
import com.usc.csci401.goatcommon.util.JsonUtils;
import com.usc.csci401.goatfacade.TournamentFacade;
import com.usc.csci401.goatservice.param.CreateTournamentParam;
import com.usc.csci401.goatservice.service.TournamentService;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

  @Autowired
  TournamentService tournamentService;

  @Autowired
  TournamentFacade tournamentFacade;

  @PostMapping("/create")
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResult<Integer> createTournament(@RequestBody CreateTournamentParam param){
    Integer res = tournamentService.createTournament(param);

    return HttpResult.success(res);
  }

  @PostMapping("/join")
  public HttpResult<Integer> joinTournament(@RequestParam Integer id){
    String username = goatContext.get("user");
    return HttpResult.success(tournamentFacade.JoinTournament(id, username));

  }

  @GetMapping("/joinable")
  public HttpResult<List<TournamentDTO>> getAllJoinable(){
      return HttpResult.success(tournamentService.getAllJoinable());
  }

  @GetMapping("/my")
  public HttpResult<Object> getMy(){
    String username = goatContext.get("user");
    return HttpResult.success(tournamentFacade.getMy(username));
  }

  @GetMapping("/play")
  public HttpResult<String> playTournament(@RequestParam Integer id){
    String username = goatContext.get("user");
    return HttpResult.success(tournamentFacade.playTournament(id, username));
  }

  @GetMapping("/bracket")
  public HttpResult<Object> getBracket(@RequestParam Integer id){
    String username = goatContext.get("user");
    JSONObject obj = tournamentFacade.getBracket(id, username);
    return HttpResult.success(JsonUtils.parseMap(obj.toString()));
  }
}
