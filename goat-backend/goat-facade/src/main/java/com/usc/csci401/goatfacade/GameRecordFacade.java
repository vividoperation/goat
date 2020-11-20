package com.usc.csci401.goatfacade;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.usc.csci401.goatcommon.dto.GameDTO;
import com.usc.csci401.goatcommon.dto.TokenMappingDTO;
import com.usc.csci401.goatcommon.exception.GameException;
import com.usc.csci401.goatcommon.util.HttpUtils;
import com.usc.csci401.goatcommon.util.JsonUtils;
import com.usc.csci401.goatdao.model.GameRecord;
import com.usc.csci401.goatdao.model.Tournament;
import com.usc.csci401.goatservice.service.GamePlayerService;
import com.usc.csci401.goatservice.service.GameRecordService;
import com.usc.csci401.goatservice.service.GameService;
import com.usc.csci401.goatservice.service.SeedingService;
import com.usc.csci401.goatservice.service.TokenMappingService;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GameRecordFacade {

 @Autowired
 private GameRecordService gameRecordService;

 @Autowired
 private GameService gameService;

 @Autowired
 private GamePlayerService gamePlayerService;

 @Autowired
 private SeedingService seedingService;

 @Autowired
 private TokenMappingService tokenMappingService;

 private final Queue<String> queue = new PriorityQueue<>();

 private final Lock lock = new ReentrantLock();

 private final Condition condVar = lock.newCondition();

 private String player1;

 private String player2;

 private Integer token1;

 private Integer token2;

 private Boolean exception = false;

 private final Lock Tlock = new ReentrantLock();

 private final Condition Tvar = Tlock.newCondition();

 private Map<Integer, Map<Integer, String>> Seed = Maps.newHashMap();

 private Map<Integer,Map<Integer, Map<Integer, List<Integer>>>> match = Maps.newHashMap();

 private final Queue<String> finalRound = new PriorityQueue<>();

 private Integer seed1;

 private Integer seed2;

 private String p1;

 private String p2;

 public String addGameRecord(String gameName, String player, Integer tournamentId, Integer round){
   Preconditions.checkArgument(StringUtils.isNotBlank(gameName));
   Preconditions.checkArgument(StringUtils.isNotBlank(player));
   player1 = null;
   player2 = null;

   GameDTO gd = gameService.getGame(gameName);
   lock.lock();
   try{
     queue.add(player);
     if(queue.size() >= 2){
       player1 = queue.poll();
       player2 = queue.poll();
       Integer gameId = gameRecordService.addGameRecord(gameName, null, null ,null , null);
       gamePlayerService.addGamePlayer(gameId, player1);
       gamePlayerService.addGamePlayer(gameId, player2);
       token1 = tokenMappingService.addTokenMapping(player1, gameId);
       token2 = tokenMappingService.addTokenMapping(player2, gameId);
       Map<String, Object> param = Maps.newHashMap();

       param.put("player1", token1);
       param.put("player2", token2);

       String backendUrl = gd.getCreategameurl();
       String res;
       try {
         res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
       } catch (Exception e) {
         exception = true;
         condVar.signalAll();
         throw new GameException("Error connecting to game backend");
       }

       if(res == null){
         exception = true;
         condVar.signalAll();
         throw new GameException("Unexpected Return from game backend");
       }
       condVar.signalAll();
     }

     while(!player.equals(player1) && !player.equals(player2)){
       try{
          condVar.await();
       }catch (Exception e) {
         log.error(e.getMessage());
       }
     }

     if(exception){
       exception = false;
       throw new GameException("Error connecting to game backend");
     }

      if (player.equals(player1)) {
         return gd.getStartgameurl() + "?token=" + token1;
       }
       return gd.getStartgameurl() + "?token=" + token2;

   }finally {
     lock.unlock();
   }
 }

   public void updateGameWinner(Integer winner){
      TokenMappingDTO tm = tokenMappingService.findByToken(winner);
      if(tm == null){
        throw new GameException("Token does not exist");
      }

      String username = tm.getUsername();
      Integer gameId = tm.getGameid();

      gameRecordService.updateWinner(username, gameId);
      tokenMappingService.deletebyToken(gameId);
   }


   public String startTournamentGame(String gameName, String player, Integer tournamentId, Integer round, Integer seed, Tournament tournament){
     Preconditions.checkArgument(StringUtils.isNotBlank(gameName));
     Preconditions.checkArgument(StringUtils.isNotBlank(player));
     seed1 = null;
     seed2 = null;
     p1 = null;
     p2 = null;

     GameDTO gd = gameService.getGame(gameName);
     Tlock.lock();
     try{
       if((tournament.getMaxplayers() == 4 && round == 2) || round == 3){
         finalRound.add(player);
         if(finalRound.size() == 2){
           p1 = finalRound.poll();
           p2 = finalRound.poll();
           seed1 = seedingService.getSeed(tournamentId, p1).getSeed();
           seed2 = seedingService.getSeed(tournamentId, p2).getSeed();
           Integer temp = seed1;
           seed1 = Math.min(seed1, seed2);
           seed2 = Math.max(temp, seed2);
         }
       }
       else {
         if(Seed.containsKey(tournamentId)){
           Seed.get(tournamentId).put(seed, player);
         }
         else {
           Map<Integer, String> maps = Maps.newHashMap();
           maps.put(seed, player);
           Seed.put(tournamentId, maps);
         }
       }

       if(tournament.getMaxplayers() == 4){
         if(round == 1) {
           if ((seed == 1 && Seed.get(tournamentId).containsKey(4)) || (seed == 4 &&  Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(4);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(4);
             seed1 = 1;
             seed2 = 4;
           } else if((seed == 2 && Seed.get(tournamentId).containsKey(3)) || (seed == 3 &&  Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(3);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(3);
             seed1 = 2;
             seed2 = 3;
           }
         }
       }

       else if(tournament.getMaxplayers() == 5){
         if(round == 1){
           if ((seed == 4 && Seed.get(tournamentId).containsKey(5)) || (seed == 5 && Seed.get(tournamentId).containsKey(4))){
             p1 = Seed.get(tournamentId).get(4);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(4);
             Seed.get(tournamentId).remove(5);
             seed1 = 4;
             seed2 = 5;
           }
         }
         else if(round == 2){
           if((seed == 1 && Seed.get(tournamentId).containsKey(4))|| (seed == 4 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(4);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(4);
             seed1 = 1;
             seed2 = 4;
           }
           else if((seed == 1 && Seed.get(tournamentId).containsKey(5))|| (seed == 5 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(5);
             seed1 = 1;
             seed2 = 5;
           }
           else if((seed == 3 && Seed.get(tournamentId).containsKey(2))|| (seed == 2 && Seed.get(tournamentId).containsKey(3))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(3);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(3);
             seed1 = 2;
             seed2 = 3;
           }
         }
       }

       else if(tournament.getMaxplayers() == 6){
         if(round == 1){
           if ((seed == 4 && Seed.get(tournamentId).containsKey(5)) || (seed == 5 && Seed.get(tournamentId).containsKey(4))){
             p1 = Seed.get(tournamentId).get(4);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(4);
             Seed.get(tournamentId).remove(5);
             seed1 = 4;
             seed2 = 5;
           }
           else if ((seed == 3 && Seed.get(tournamentId).containsKey(6)) || (seed == 6 && Seed.get(tournamentId).containsKey(3))){
             p1 = Seed.get(tournamentId).get(3);
             p2 = Seed.get(tournamentId).get(6);
             Seed.get(tournamentId).remove(3);
             Seed.get(tournamentId).remove(6);
             seed1 = 3;
             seed2 = 6;
           }
         }
         else if(round == 2) {
           if((seed == 1 && Seed.get(tournamentId).containsKey(4))|| (seed == 4 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(4);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(4);
             seed1 = 1;
             seed2 = 4;
           }
           else if((seed == 1 && Seed.get(tournamentId).containsKey(5))|| (seed == 5 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(5);
             seed1 = 1;
             seed2 = 5;
           }
           else if((seed == 2 && Seed.get(tournamentId).containsKey(3))|| (seed == 3 && Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(3);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(3);
             seed1 = 2;
             seed2 = 3;
           }
           else if((seed == 2 && Seed.get(tournamentId).containsKey(6))|| (seed == 6 && Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(6);
             Seed.get(tournamentId).remove(6);
             Seed.get(tournamentId).remove(2);
             seed1 = 2;
             seed2 = 6;
           }
         }
       }

       else if(tournament.getMaxplayers() == 7){
         if(round == 1){
           if ((seed == 4 && Seed.get(tournamentId).containsKey(5)) || (seed == 5 && Seed.get(tournamentId).containsKey(4))){
             p1 = Seed.get(tournamentId).get(4);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(4);
             Seed.get(tournamentId).remove(5);
             seed1 = 4;
             seed2 = 5;
           }
           else if ((seed == 3 && Seed.get(tournamentId).containsKey(6)) || (seed == 6 && Seed.get(tournamentId).containsKey(3))){
             p1 = Seed.get(tournamentId).get(4);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(4);
             Seed.get(tournamentId).remove(5);
             seed1 = 3;
             seed2 = 6;
           }
           else if ((seed == 2 && Seed.get(tournamentId).containsKey(7)) || (seed == 2 && Seed.get(tournamentId).containsKey(7))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(7);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(7);
             seed1 = 2;
             seed2 = 7;
           }
         }
         else if(round == 2){
           if((seed == 1 && Seed.get(tournamentId).containsKey(4))|| (seed == 4 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(4);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(4);
             seed1 = 1;
             seed2 = 4;
           }
           else if((seed == 1 && Seed.get(tournamentId).containsKey(5))|| (seed == 5 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(5);
             seed1 = 1;
             seed2 = 5;
           }
           else if((seed == 2 && Seed.get(tournamentId).containsKey(3))|| (seed == 3 && Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(3);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(3);
             seed1 = 2;
             seed2 = 3;
           }
           else if((seed == 2 && Seed.get(tournamentId).containsKey(6))|| (seed == 6 && Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(6);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(6);
             seed1 = 2;
             seed2 = 6;
           }
           else if((seed == 7 && Seed.get(tournamentId).containsKey(3))|| (seed == 3 && Seed.get(tournamentId).containsKey(7))){
             p1 = Seed.get(tournamentId).get(7);
             p2 = Seed.get(tournamentId).get(3);
             Seed.get(tournamentId).remove(3);
             Seed.get(tournamentId).remove(7);
             seed1 = 3;
             seed2 = 7;
           }
           else if((seed == 7 && Seed.get(tournamentId).containsKey(6))|| (seed == 6 && Seed.get(tournamentId).containsKey(7))){
             p1 = Seed.get(tournamentId).get(6);
             p2 = Seed.get(tournamentId).get(7);
             Seed.get(tournamentId).remove(7);
             Seed.get(tournamentId).remove(6);
             seed1 = 6;
             seed2 = 7;
           }
         }
       }

       else if(tournament.getMaxplayers() == 8){
         if(round == 1){
           if ((seed == 4 && Seed.get(tournamentId).containsKey(5)) || (seed == 5 && Seed.get(tournamentId).containsKey(4))){
             p1 = Seed.get(tournamentId).get(4);
             player2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(4);
             Seed.get(tournamentId).remove(5);
             seed1 = 4;
             seed2 = 5;
           }
           else if ((seed == 3 && Seed.get(tournamentId).containsKey(6)) || (seed == 6 && Seed.get(tournamentId).containsKey(3))){
             p1 = Seed.get(tournamentId).get(3);
             p2 = Seed.get(tournamentId).get(6);
             Seed.get(tournamentId).remove(3);
             Seed.get(tournamentId).remove(6);
             seed1 = 3;
             seed2 = 6;
           }
           else if ((seed == 2 && Seed.get(tournamentId).containsKey(7)) || (seed == 2 && Seed.get(tournamentId).containsKey(7))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(7);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(7);
             seed1 = 2;
             seed2 = 7;
           }
           else if ((seed == 1 && Seed.get(tournamentId).containsKey(8)) || (seed == 8 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(8);
             Seed.get(tournamentId).remove(8);
             Seed.get(tournamentId).remove(1);
             seed1 = 1;
             seed2 = 8;
           }
         }
         else if(round == 2){
           if((seed == 1 && Seed.get(tournamentId).containsKey(4))|| (seed == 4 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(4);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(4);
             seed1 = 1;
             seed2 = 4;
           }
           else if((seed == 1 && Seed.get(tournamentId).containsKey(5))|| (seed == 5 && Seed.get(tournamentId).containsKey(1))){
             p1 = Seed.get(tournamentId).get(1);
             p2 = Seed.get(tournamentId).get(5);
             Seed.get(tournamentId).remove(1);
             Seed.get(tournamentId).remove(5);
             seed1 = 1;
             seed2 = 5;
           }
           else if((seed == 8 && Seed.get(tournamentId).containsKey(4))|| (seed == 4 && Seed.get(tournamentId).containsKey(8))){
             p1 = Seed.get(tournamentId).get(4);
             p2 = Seed.get(tournamentId).get(8);
             Seed.get(tournamentId).remove(4);
             Seed.get(tournamentId).remove(8);
             seed1 = 4;
             seed2 = 8;
           }
           else if((seed == 8 && Seed.get(tournamentId).containsKey(5))|| (seed == 5 && Seed.get(tournamentId).containsKey(8))){
             p1 = Seed.get(tournamentId).get(5);
             p2 = Seed.get(tournamentId).get(8);
             Seed.get(tournamentId).remove(8);
             Seed.get(tournamentId).remove(5);
             seed1 = 5;
             seed2 = 8;
           }
           else if((seed == 2 && Seed.get(tournamentId).containsKey(3))|| (seed == 3 && Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(3);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(3);
             seed1 = 2;
             seed2 = 3;
           }
           else if((seed == 2 && Seed.get(tournamentId).containsKey(6))|| (seed == 6 && Seed.get(tournamentId).containsKey(2))){
             p1 = Seed.get(tournamentId).get(2);
             p2 = Seed.get(tournamentId).get(6);
             Seed.get(tournamentId).remove(2);
             Seed.get(tournamentId).remove(6);
             seed1 = 2;
             seed2 = 6;
           }
           else if((seed == 7 && Seed.get(tournamentId).containsKey(3))|| (seed == 3 && Seed.get(tournamentId).containsKey(7))){
             p1 = Seed.get(tournamentId).get(3);
             p2 = Seed.get(tournamentId).get(7);
             Seed.get(tournamentId).remove(3);
             Seed.get(tournamentId).remove(7);
             seed1 = 3;
             seed2 = 7;
           }
           else if((seed == 7 && Seed.get(tournamentId).containsKey(6))|| (seed == 6 && Seed.get(tournamentId).containsKey(7))){
             p1 = Seed.get(tournamentId).get(6);
             p2 = Seed.get(tournamentId).get(7);
             Seed.get(tournamentId).remove(7);
             Seed.get(tournamentId).remove(6);
             seed1 = 6;
             seed2 = 7;
           }


         }
       }

       if(p1 != null && p2 != null) {
         GameRecord gameRecord = gameRecordService.getRecord(tournamentId, seed1, seed2);
//         if(gameRecord != null){
//           List<GameRecord> records = gameRecordService.findTournamentGame(tournamentId);
//           records.forEach(record -> {
//             gameRecordService.updateWinner(null, record.getGameid());
//           });
//         }

         Integer gameId = gameRecordService
               .addGameRecord(gameName, tournamentId, round, seed1, seed2);
         gamePlayerService.addGamePlayer(gameId, p1);
         gamePlayerService.addGamePlayer(gameId, p2);
         token1 = tokenMappingService.addTokenMapping(p1, gameId);
         token2 = tokenMappingService.addTokenMapping(p2, gameId);
         Map<String, Object> param = Maps.newHashMap();

         param.put("player1", token1);
         param.put("player2", token2);

         String backendUrl = gd.getCreategameurl();
         String res;
         try {
           res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
         } catch (Exception e) {
           exception = true;
           Tvar.signalAll();
           throw new GameException("Error connecting to game backend");
         }

         if (res == null) {
           exception = true;
           Tvar.signalAll();
           throw new GameException("Unexpected Return from game backend");
         }
         Tvar.signalAll();
       }

       while(!player.equals(p1) && !player.equals(p2)){
         try{
           Tvar.await();
         }catch (Exception e) {
           log.error(e.getMessage());
         }
       }

       if(exception){
         exception = false;
         throw new GameException("Error connecting to game backend");
       }

       if (player.equals(p1)) {
         return gd.getStartgameurl() + "?token=" + token1;
       }
       return gd.getStartgameurl() + "?token=" + token2;

     }finally {
       Tlock.unlock();
     }
   }

 }
