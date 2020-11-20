package com.usc.csci401.goatfacade;

import com.google.common.base.Preconditions;
import com.usc.csci401.goatcommon.exception.TournamentException;
import com.usc.csci401.goatdao.model.GameRecord;
import com.usc.csci401.goatdao.model.Seeding;
import com.usc.csci401.goatdao.model.Tournament;
import com.usc.csci401.goatservice.service.GamePlayerService;
import com.usc.csci401.goatservice.service.GameRecordService;
import com.usc.csci401.goatservice.service.SeedingService;
import com.usc.csci401.goatservice.service.TournamentService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TournamentFacade {

  @Autowired
  private SeedingService seedingService;

  @Autowired
  private TournamentService tournamentService;

  @Autowired
  private GameRecordFacade gameRecordFacade;

  @Autowired
  private GameRecordService gameRecordService;

  @Autowired
  private GamePlayerService gamePlayerService;

  private boolean alive = true;

  public List<Tournament> getMy(String username) {
    List<Seeding> seedings = seedingService.getByUsername(username);
    List<Tournament> tournaments = new ArrayList<Tournament>();
    for (Seeding seeding : seedings)
    {
      Tournament tournament = tournamentService.getById(seeding.getTournamentid());
      tournaments.add(tournament);
    }
    return tournaments;
  }

  public Integer JoinTournament(Integer tournamendId, String username) {
    Integer maxPlayer = tournamentService.getById(tournamendId).getMaxplayers();
    Integer res = seedingService.createSeeding(tournamendId, username, maxPlayer);
    tournamentService.updateCurrentPlayer(tournamendId, 1);

    return res;
  }

  public String playTournament(Integer id, String username) {
    Preconditions.checkArgument(id != null, "Torunament id cannot be null");
    Tournament tournament = tournamentService.getById(id);

    if (tournament.getMaxplayers() > tournament.getCurrentplayers()) {
      throw new TournamentException("Tournament player is not full, cannot start game");
    }

    Seeding seeding = seedingService.getSeed(id, username);
    Integer seed = seeding.getSeed();
    Integer round = seeding.getRound();
    String result = gameRecordFacade
        .startTournamentGame(tournament.getGamename(), username, id,
            round, seed, tournament);

    if (result != null) {
      seeding.setRound(round + 1);
      seedingService.updateRound(seeding);
    }
    return result;
  }

  public JSONObject getBracket(Integer id, String player) {
    Tournament tournament = tournamentService.getById(id);
    JSONObject res = new JSONObject();
    int index = 1;
    if (tournament.getMaxplayers() == 4) {
      JSONObject seed1 = createSeed(id, null, null,1, 4, index, false, tournament.getTournamentname(), player);
      ++index;
      JSONObject seed2 = createSeed(id, null, null, 2, 3, index, false, tournament.getTournamentname(), player);
      ++index;
      GameRecord gameRecord1 = gameRecordService.getRecord(id, 1, 4);
      GameRecord gameRecord2 = gameRecordService.getRecord(id, 2, 3);
      Integer team1 = null, team2 = null;
      if(gameRecord1 != null && gameRecord1.getWinner() != null){
        team1 = seedingService.getSeed(id, gameRecord1.getWinner()).getSeed();
      }
      if(gameRecord2 != null && gameRecord2.getWinner() != null){
        team2 = seedingService.getSeed(id, gameRecord2.getWinner()).getSeed();
      }

      res = createSeed(id, seed1, seed2, team1, team2 ,index, true, tournament.getTournamentname(), player);

    } else if (tournament.getMaxplayers() == 5) {
      JSONObject seed1 = createSeed(id, null, null, 4, 5, index, false, null, player);
      ++index;
      JSONObject seed2 = createSeed(id, null, null, 2, 3, index, false, null, player);
      ++index;
      GameRecord gameRecord1 = gameRecordService.getRecord(id, 4, 5);
      GameRecord gameRecord2 = gameRecordService.getRecord(id, 2, 3);
      JSONObject seed3;
      Integer team1 = null, team2 = null;
      if(gameRecord1 != null && gameRecord1.getWinner() != null){
        team1 = seedingService.getSeed(id, gameRecord1.getWinner()).getSeed();
      }
      if(gameRecord2 != null && gameRecord2.getWinner() != null){
        team2 = seedingService.getSeed(id, gameRecord2.getWinner()).getSeed();
      }
      seed3 = createSeed(id, seed1, null, team1, 1, index, false, null, player);
      ++index;
      GameRecord gameRecord3 = gameRecordService.getRecord(id, team1, 1);
      Integer winner1 = null;
      if(gameRecord3 != null && gameRecord3.getWinner() != null){
        winner1 = seedingService.getSeed(id, gameRecord3.getWinner()).getSeed();
      }
      res = createSeed(id, seed2, seed3, team2, winner1, index, true, tournament.getTournamentname(), player);
    } else if (tournament.getMaxplayers() == 6) {
      JSONObject seed1 = createSeed(id, null, null, 4, 5, index, false, null, player);
      ++index;
      JSONObject seed2 = createSeed(id, null, null, 3, 6, index, false, null, player);
      ++index;
      GameRecord gameRecord1 = gameRecordService.getRecord(id, 4, 5);
      GameRecord gameRecord2 = gameRecordService.getRecord(id, 3, 6);
      Integer team1 = null, team2 = null;
      if(gameRecord1 != null && gameRecord1.getWinner() != null){
        team1 = seedingService.getSeed(id, gameRecord1.getWinner()).getSeed();
      }
      if(gameRecord2 != null && gameRecord2.getWinner() != null){
        team2 = seedingService.getSeed(id, gameRecord2.getWinner()).getSeed();
      }
      JSONObject seed3 = createSeed(id, null, seed1, 1, team1, index, false, null, player);
      ++index;
      JSONObject seed4 = createSeed(id, null, seed2, 2, team2, index, false, null, player);
      ++index;
      GameRecord gameRecord3 = gameRecordService.getRecord(id, 1, team1);
      GameRecord gameRecord4 = gameRecordService.getRecord(id, 2, team2);
      Integer winner3 = null, winner4 = null;
      if(gameRecord3 != null && gameRecord3.getWinner() != null){
        winner3 = seedingService.getSeed(id, gameRecord3.getWinner()).getSeed();
      }
      if(gameRecord4 != null && gameRecord4.getWinner() != null){
        winner4 = seedingService.getSeed(id, gameRecord4.getWinner()).getSeed();
      }
        res = createSeed(id, seed3, seed4, winner3, winner4, index, true, null, player);
    } else if (tournament.getMaxplayers() == 7) {
      JSONObject seed1 = createSeed(id, null, null, 4, 5, index, false, null, player);
      ++index;
      JSONObject seed2 = createSeed(id, null, null, 3, 6, index, false, null, player);
      ++index;
      JSONObject seed3 = createSeed(id, null, null, 2, 7, index, false, null, player);
      ++index;
      GameRecord gameRecord1 = gameRecordService.getRecord(id, 4, 5);
      GameRecord gameRecord2 = gameRecordService.getRecord(id, 3, 6);
      GameRecord gameRecord3 = gameRecordService.getRecord(id, 2, 7);
      Integer team1 = null, team2 = null, team3 = null;
      if(gameRecord1 != null && gameRecord1.getWinner() != null){
        team1 = seedingService.getSeed(id, gameRecord1.getWinner()).getSeed();
      }
      if(gameRecord2 != null && gameRecord2.getWinner() != null){
        team2 = seedingService.getSeed(id, gameRecord2.getWinner()).getSeed();
      }
      if(gameRecord3 != null && gameRecord3.getWinner() != null){
        team3 = seedingService.getSeed(id, gameRecord3.getWinner()).getSeed();
      }
      JSONObject seed4 = createSeed(id, null, seed1, 1, team1, index, false, null, player);
      ++index;
      JSONObject seed5 = createSeed(id, seed2, seed3, team2, team3, index, false, null, player);
      ++index;
      GameRecord gameRecord4 = gameRecordService.getRecord(id, 1, team1);
      GameRecord gameRecord5 = gameRecordService.getRecord(id, team2, team3);
      Integer team4 = null, team5 = null;
      if(gameRecord4 != null && gameRecord4.getWinner() != null){
        team4 = seedingService.getSeed(id, gameRecord4.getWinner()).getSeed();
      }
      if(gameRecord5 != null && gameRecord5.getWinner() != null){
        team5 = seedingService.getSeed(id, gameRecord5.getWinner()).getSeed();
      }

      res = createSeed(id, seed4, seed5, team4, team5, index, true, tournament.getTournamentname(), player);

    } else if (tournament.getMaxplayers() == 8) {
      JSONObject seed1 = createSeed(id, null, null, 4, 5, index, false, null, player);
      ++index;
      JSONObject seed2 = createSeed(id, null, null, 3, 6, index, false, null, player);
      ++index;
      JSONObject seed3 = createSeed(id, null, null, 2, 7, index, false, null, player);
      ++index;
      JSONObject seed4 = createSeed(id, null, null, 1,8, index, false, null, player);
      ++index;
      GameRecord gameRecord1 = gameRecordService.getRecord(id, 4, 5);
      GameRecord gameRecord2 = gameRecordService.getRecord(id, 3, 6);
      GameRecord gameRecord3 = gameRecordService.getRecord(id, 2, 7);
      GameRecord gameRecord4 = gameRecordService.getRecord(id, 1, 8);
      Integer team1 = null, team2 = null, team3 = null, team4 = null;
      if(gameRecord1 != null && gameRecord1.getWinner() != null){
        team1 = seedingService.getSeed(id, gameRecord1.getWinner()).getSeed();
      }
      if(gameRecord2 != null && gameRecord2.getWinner() != null){
        team2 = seedingService.getSeed(id, gameRecord2.getWinner()).getSeed();
      }
      if(gameRecord3 != null && gameRecord3.getWinner() != null){
        team3 = seedingService.getSeed(id, gameRecord3.getWinner()).getSeed();
      }
      if(gameRecord4 != null && gameRecord4.getWinner() != null){
        team4 = seedingService.getSeed(id, gameRecord4.getWinner()).getSeed();
      }
      JSONObject seed5 = createSeed(id, seed1, seed4, team1, team4, index, false, null, player);
      JSONObject seed6 = createSeed(id, seed2, seed3, team2, team3, index, false, null, player);
      GameRecord gameRecord5 = gameRecordService.getRecord(id, team1, team4);
      GameRecord gameRecord6 = gameRecordService.getRecord(id, team2, team3);
      Integer team5 = null, team6 = null;
      if(gameRecord5 != null && gameRecord5.getWinner() != null){
        team5 = seedingService.getSeed(id, gameRecord5.getWinner()).getSeed();
      }
      if(gameRecord6 != null && gameRecord6.getWinner() != null){
        team6 = seedingService.getSeed(id, gameRecord6.getWinner()).getSeed();
      }
      res = createSeed(id, seed5, seed6, team5, team6, index, true, tournament.getTournamentname(), player);
    }

    return res;
  }

    private JSONObject createSeed(Integer tournamentId, JSONObject seed1, JSONObject seed2, Integer team1, Integer team2, Integer index,
        boolean finalGame, String tournamentName, String player) {
      JSONObject name1 = new JSONObject();
      GameRecord gameRecord = gameRecordService.getRecord(tournamentId, team1, team2);

      String player1 = seedingService.getUsername(tournamentId, team1);
      String player2 = seedingService.getUsername(tournamentId, team2);
      JSONObject visitor = new JSONObject();
      JSONObject home = new JSONObject();
      JSONObject obj3 = new JSONObject();
      JSONObject obj4 = new JSONObject();
      if (team1 != null) {
        name1.put("name", player1);
        home.put("team", name1);
      }
      JSONObject name2 = new JSONObject();
      if (team2 != null) {
        name2.put("name", player2);
        visitor.put("team", name2);
      }
      if (gameRecord != null && gameRecord.getWinner() != null) {
        if (gameRecord.getWinner().equals(player1)) {
        obj3.put("score", 1);
        home.put("score", obj3);
        obj4.put("score", 0);
        visitor.put("score", obj4);
      } else {
        obj3.put("score", 1);
        visitor.put("score", obj3);
        obj4.put("score", 0);
        home.put("score", obj4);
      }
        if(player.equals(player1) || player.equals(player2)){
          if(!gameRecord.getWinner().equals(player)){
            alive = false;
          }
        }
    }
      if(seed1 != null) {
        home.put("seed", seed1);
      }
      if(seed2 != null) {
        visitor.put("seed", seed2);
      }
      JSONObject sides = new JSONObject();
      sides.put("home", home);
      sides.put("visitor", visitor);
      JSONObject sourceGame = new JSONObject();
      sourceGame.put("id",index);
      sourceGame.put("sides", sides);
      JSONObject seed = new JSONObject();
      if(!finalGame) {
        seed.put("rank", 1);
        seed.put("sourceGame", sourceGame);
      }
      else{
        seed.put("bracket", sourceGame);
        seed.put("alive", alive);
        seed.put("name", tournamentName);
        alive = true;
      }
      if (gameRecord != null && gameRecord.getWinner() != null && finalGame) {
        seed.put("winner", gameRecord.getWinner());
      }

      Tournament tournament = tournamentService.getById(tournamentId);
      seed.put("full", tournament.getCurrentplayers() >= tournament.getMaxplayers());

      return seed;
    }




}
