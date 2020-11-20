// package com.usc.csci401.testgameweb.controller;

// import com.lambdaworks.redis.*;
// import com.usc.csci401.testgameweb.utils.HttpResult;
// import com.usc.csci401.testgameweb.utils.JsonUtils;
// import com.usc.csci401.testgameweb.params.TestParam;
// import com.usc.csci401.testgameweb.controller.GameSessionHandler;
// import javax.annotation.Resource;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
// import java.util.HashSet;
// import lombok.RequiredArgsConstructor;
// import org.springframework.http.HttpStatus;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.validation.annotation.Validated;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseStatus;
// import org.springframework.web.bind.annotation.RestController;


// import javax.websocket.OnClose;
// import javax.websocket.OnError;
// import javax.websocket.OnMessage;
// import javax.websocket.OnOpen;
// import javax.websocket.Session;
// import javax.websocket.server.ServerEndpoint;
// import javax.inject.Inject;

// import java.io.StringReader;
// //import org.json.simple.JSONObject;
// ////import org.json.simple.JSONReader;
// //import com.fasterxml.jackson.databind.JsonObject;
// //import com.fasterxml.jackson.databind.JsonReader;

// import javax.enterprise.context.ApplicationScoped;
// import javax.json.Json;
// import javax.json.JsonObject;
// import javax.json.JsonReader;
// import com.usc.csci401.testgameweb.controller.GameModel;

// @RestController
// @RequiredArgsConstructor
// @ApplicationScoped
// @ServerEndpoint("/testgameBack")
// public class WebSocketServer {

//     private Set <Integer> gamesInSession = new HashSet<>();

// //    @Inject
//     private GameSessionHandler sessionHandler;

//     @Value("${redis.url}")
//     private String redisURL;

//     @OnOpen
//     public void open(Session session) {
// //        Reads device actions and attributes sent from the client.
//         //Invokes the session handler to perform the proper
//         // operations on the specified Device object.
//         // In this application, the add action sent from the client
//         // invokes the addDevice method, the remove action invokes
//         // the removeDevice method, and the toggle action invokes
//         // the toggleDevice method.
//         System.out.println("in open in websocket");

//         sessionHandler.addSession(session);
//     }

//     @OnClose
//     public void close(Session session) {
//         sessionHandler.removeSession(session);
//         System.out.println("close");

//     }
//     @OnError
//     public void onError(Throwable error) {
//         System.out.println("error");

// //        Logger.getLogger(WebSocketServer.class.getName()).log(Level.SEVERE, null, error);
//     }

//     private Integer add_check_redis(String token){
//         https://www.javainuse.com/spring/boot-websocket
//         System.out.println("ac redis");

//         RedisClient redisClient = new RedisClient(
//                 RedisURI.create(redisURL));
//         RedisConnection<String, String> connection = redisClient.connect();

//         int setSize_before = gamesInSession.size();
//         List<String> list = connection.keys("*");
//         System.out.println(list.size());
//         for (int i = 0; i < list.size(); i++)
//         {
//             if(list.get(i) == token){
//                 gamesInSession.add(Integer.parseInt(connection.get(token)));
//             }
//         }

//         connection.close();
//         redisClient.shutdown();
//         if(gamesInSession.size() == setSize_before)
//         {
//             return 2;
//         }


//         return 1;
//     }
//     private Integer get_redis_id(String token){

//         RedisClient redisClient = new RedisClient(
//                 RedisURI.create(redisURL));
//         RedisConnection<String, String> connection = redisClient.connect();

//         Integer id = 1;
//         List<String> list = connection.keys("*");
//         System.out.println(list.size());
//         for (int i = 0; i < list.size(); i++)
//         {
//             if(list.get(i) == token){
//                id =  Integer.parseInt(connection.get(token));
//             }
//         }

//         connection.close();
//         redisClient.shutdown();

//         return id;
//     }
//     @OnMessage
//     public void handleMessage(String message, Session session) {
//         System.out.println("in handle message");

//         try (JsonReader reader = Json.createReader(new StringReader(message))) {

//             JsonObject jsonMessage = reader.readObject();

//             //return 1 if only
//             Integer playerCount = add_check_redis((jsonMessage.get("token")).toString());
//             Integer gameId = get_redis_id((jsonMessage.get("token")).toString());

//             String status = "";
//             if ("start".equals(jsonMessage.getString("action"))) {
//                 sessionHandler.addSession(session);
//                 GameModel device = new GameModel();
//                 System.out.println("in start message");
//                 if (playerCount == 1)
//                 {
//                     status = "wait";
//                 }
//                 else if(playerCount == 2)
//                 {
//                     status = "start";
//                 }
//                 device.setGameId(gameId.toString());
//                 device.setStatus(status);
//                 sessionHandler.addGame(device);
//             }

//             if ("remove".equals(jsonMessage.getString("action"))) {
//                 int id = (int) jsonMessage.getInt("id");
//                 int token = (int) jsonMessage.getInt("token");
//                 sessionHandler.removeGame(id, token);
// //                close(session);
//             }

//             if ("toggle".equals(jsonMessage.getString("action"))) {
//                 int id = (int) jsonMessage.getInt("id");
//                 int token = (int) jsonMessage.getInt("token");
//                 sessionHandler.toggleGame(id, token);
//             }
//         }
//     }
// }
// //