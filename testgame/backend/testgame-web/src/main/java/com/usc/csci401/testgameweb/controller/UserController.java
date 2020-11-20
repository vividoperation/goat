package com.usc.csci401.testgameweb.controller;

import com.lambdaworks.redis.*;
import com.usc.csci401.testgameweb.params.GameParam;
import com.usc.csci401.testgameweb.utils.HttpResult;
import com.usc.csci401.testgameweb.params.TestParam;
//import com.usc.csci401.testgameweb.controller.WebSocketServer;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Date;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

  //move into redis cache class
	@Value("${redis.url}")
	private String redisURL;


//  @GetMapping("/connecttoredis")
//  public HttpResult<String> connectToRedis() {
//    RedisClient redisClient = new RedisClient(
//      RedisURI.create(redisURL));
//    RedisConnection<String, String> connection = redisClient.connect();
//
//    System.out.println("Connected to Redis");
//
//    connection.close();
//    redisClient.shutdown();
//    return HttpResult.success("Connected to Redis");
//  }

  @PostMapping("/newgame")
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResult<String> newgame(@RequestBody @Validated GameParam param){

    System.out.println("in new game");

    //move redis connection into redis object

    RedisClient redisClient = new RedisClient(
            RedisURI.create(redisURL));
    RedisConnection<String, String> connection = redisClient.connect();

    Random rand = new Random();
    int gameId = rand.nextInt(Integer.MAX_VALUE);
    //put this stuff in redis function


//    Create a game id or a hash and save list of tokens in the game id
//    Install redis on computer
//    Or use in docker container
//
//    Called from minghao in create game end point, integer tokens
//    Send something back that says ok or 1, http204:nocontent response
//    Just need to know that ok it got it and is ready for the gam

    System.out.println("Connected to Redis");
    System.out.println("xx1234: " + param);
    connection.set(param.getPlayer1().toString(), String.valueOf(gameId));
    connection.set(param.getPlayer2().toString(), String.valueOf(gameId));

    //    Create a game id or a hash and save list of tokens in the game id
    //    Install redis on computer
    //    Or use in docker container
    //
    //    Called from minghao in create game end point, integer tokens
    //    Send something back that says ok or 1, http204:nocontent response
    //    Just need to know that ok it got it and is ready for the gam

    List<String> list = connection.keys("*");
    System.out.println("TOKENS");
    System.out.println(list.size());
    for (int i = 0; i < list.size(); i++)
    {
      System.out.println("Stored Keys:: " + list.get(i));
    }

    System.out.println(new Date() + " NEW GAME CREATED");
    connection.close();
    redisClient.shutdown();
    return HttpResult.success("New Game Created Successfully");
  }

  @PostMapping("/startnewgame")
  @ResponseStatus(HttpStatus.CREATED)
  public HttpResult<String> newgame(){

    System.out.println("in new game");
    RedisClient redisClient = new RedisClient(
            RedisURI.create(redisURL));
    RedisConnection<String, String> connection = redisClient.connect();

    System.out.println("Connected to Redis");


//    Create a game id or a hash and save list of tokens in the game id
//    Install redis on computer
//    Or use in docker container
//
//    Called from minghao in create game end point, integer tokens
//    Send something back that says ok or 1, http204:nocontent response
//    Just need to know that ok it got it and is ready for the gam
    List<String> list = connection.keys("*");
    System.out.println("TOKENS");
    System.out.println(list.size());
    String keyToReturn = list.get(0);



    System.out.println("winner declared");
    connection.close();
    redisClient.shutdown();
    return HttpResult.success(keyToReturn);
  }
//  @PostMapping("/newgame")
//  @ResponseStatus(HttpStatus.CREATED)
//  public HttpResult<String> newgame(String jsonString){//@RequestBody @Validated TestParam param){
//
//    System.out.println("jsonstring...");
//    System.out.println(jsonString);
////    Parse
//    Map<String, Integer> tokens = JsonUtils.parseMap(jsonString);
//
//    //player 1, player 2, game id
////    Gets list of tokens, take list of tokens, save in the redis cache
////    Redis --> in memory key value store --> type of nosql database
//    Integer token1 = 0;
//    Integer token2 = 0;
//    Integer gameId = 0;
//    for (Map.Entry<String, Integer> entry : tokens.entrySet()) {
//     System.out.println(entry.getKey());
//      if (entry.getKey().equals("Player1"))
//      {
//        System.out.println("here1");
//        token1 = entry.getValue();
//      }
//      else if(entry.getKey().equals("Player2"))
//      {
//        System.out.println("here2");
//        token2 = entry.getValue();
//      }
//      else if(entry.getKey().equals("Game"))
//      {
//        System.out.println("here3");
//        gameId= entry.getValue();
//      }
//    }
//
//    RedisClient redisClient = new RedisClient(
//            RedisURI.create(redisURL));
//    RedisConnection<String, String> connection = redisClient.connect();
//
//
//
//    System.out.println("Connected to Redis");
//    connection.set(token1.toString(), gameId.toString());
//    connection.set(token2.toString(), gameId.toString());
////    for (int i = 0; i < tokens.length(); i++)
////      connection.set(tokens[i].toString(), gameID.toString());
////    Create a game id or a hash and save list of tokens in the game id
////    Install redis on computer
////    Or use in docker container
////
////    Called from minghao in create game end point, integer tokens
////    Send something back that says ok or 1, http204:nocontent response
////    Just need to know that ok it got it and is ready for the gam
//    System.out.println("TOKENS");
//    List<String> list = connection.keys("*");
//    System.out.println(list.size());
//    for (int i = 0; i < list.size(); i++)
//    {
//        System.out.println("Stored Keys:: " + list.get(i));
//    }
//
//    System.out.println("NEW GAME CREATED");
//    connection.close();
//    redisClient.shutdown();
//    return HttpResult.success("created new game");
//  }
}
