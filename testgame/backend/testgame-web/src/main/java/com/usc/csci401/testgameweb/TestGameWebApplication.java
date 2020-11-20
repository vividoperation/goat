package com.usc.csci401.testgameweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Map;
import java.util.HashMap;

import com.usc.csci401.testgameweb.utils.HttpResult;
import com.usc.csci401.testgameweb.utils.JsonUtils;
import com.usc.csci401.testgameweb.params.TestParam;
import org.springframework.http.HttpStatus;
import com.usc.csci401.testgameweb.utils.HttpUtils;


@SpringBootApplication(scanBasePackages = {"com.usc.csci401"})
public class TestGameWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestGameWebApplication.class, args);
		//run on server start up, auto add the 10 games
		//add code to add in 10 games

		//interact with redis object, add to redis cache with two integers, player 1 and player 2
		// controller then parses from HTTP post request then it calls interactive
		//Class RedisCache
		//createGame(int gameToken, int player1token, int player2Token);
//		player1 = queue.poll();
//		player2 = queue.poll();
//		Integer gameId = gameRecordService.addGameRecord(gameName);
//		gamePlayerService.addGamePlayer(gameId, player1);
//		gamePlayerService.addGamePlayer(gameId, player2);
//		token1 = tokenMappingService.addTokenMapping(player1, gameId);
//		token2 = tokenMappingService.addTokenMapping(player2, gameId);

// 		Map<String, Integer> param = new HashMap<>();
// 		Integer token1 = 1;
// 		Integer token2 = 2;
// 		param.put("player1", token1);
// 		param.put("player2", token2);

// 		String backendUrl = "http://localhost:8081/newgame";
// 		String res;
// //		String backendUrl = gd.getCreategameurl();
// //		String res;
// 		try {
// 			res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
// 		} catch (Exception e) {
// 		}


// 		param = new HashMap<>();
// 		token1 = 3;
// 		token2 = 4;
// 		param.put("player1", token1);
// 		param.put("player2", token2);

// //		String backendUrl = "http://localhost:8081/newgame";
// //		res ="";
// //		String backendUrl = gd.getCreategameurl();
// //		String res;
// 		try {
// 			res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
// 		} catch (Exception e) {
// 		}


// 		param = new HashMap<>();
// 		token1 = 5;
// 		token2 = 6;
// 		param.put("player1", token1);
// 		param.put("player2", token2);

// //		String backendUrl = "http://localhost:8081/newgame";
// //		String res;
// //		String backendUrl = gd.getCreategameurl();
// //		String res;
// 		try {
// 			res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
// 		} catch (Exception e) {
// 		}

// 		param = new HashMap<>();
// 		token1 = 7;
// 		token2 = 8;
// 		param.put("player1", token1);
// 		param.put("player2", token2);

// //		String backendUrl = "http://localhost:8081/newgame";
// //		String res;
// //		String backendUrl = gd.getCreategameurl();
// //		String res;
// 		try {
// 			res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
// 		} catch (Exception e) {
// 		}

// 		param = new HashMap<>();
// 		token1 = 9;
// 		token2 = 10;
// 		param.put("player1", token1);
// 		param.put("player2", token2);

// //		String backendUrl = "http://localhost:8081/newgame";
// //		String res;
// //		String backendUrl = gd.getCreategameurl();
// //		String res;
// 		try {
// 			res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
// 		} catch (Exception e) {

// 		}


// 		param = new HashMap<>();
// 		token1 = 11;
// 		token2 = 12;
// 		param.put("player1", token1);
// 		param.put("player2", token2);

// //		String backendUrl = "http://localhost:8081/newgame";
// //		String res;
// //		String backendUrl = gd.getCreategameurl();
// //		String res;
// 		try {
// 			res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.CREATED);
// 		} catch (Exception e) {

// 		}


// 		while(true){
// 			int x = 1+1;
// 		}

//		return 0;
		//http://localhost:5000?token=1

		//check that they are not in redis cache when done
	}

}
