package com.usc.csci401.testgameweb.controller;

import com.lambdaworks.redis.*;
import com.usc.csci401.testgameweb.utils.HttpResult;
import com.usc.csci401.testgameweb.utils.JsonUtils;
import com.usc.csci401.testgameweb.params.TestParam;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import javax.websocket.Session;
import com.usc.csci401.testgameweb.controller.GameModel;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

@ApplicationScoped
public class GameSessionHandler {
    private int gameId = 0;
    private final Set<Session> sessions = new HashSet<>();
    private final Set<GameModel> games = new HashSet<>();

    @Value("${redis.url}")
    private String redisURL;


    private void removeFromRedis(int token){
        RedisClient redisClient = new RedisClient(
                RedisURI.create(redisURL));
        RedisConnection<String, String> connection = redisClient.connect();

        List<String> list = connection.keys("*");
        List<Integer> players;
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++)
        {
            if(Integer.parseInt(list.get(i)) == token){
                connection.del(list.get(i));
            }
        }

        connection.close();
        redisClient.shutdown();


    }
    private List<Integer> getResults(String id){
        RedisClient redisClient = new RedisClient(
                RedisURI.create(redisURL));
        RedisConnection<String, String> connection = redisClient.connect();

        List<String> list = connection.keys("*");
        List<Integer> players = null;
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++)
        {
            if(connection.get(list.get(i)) == id){
                players.add(Integer.parseInt(list.get(i)));
            }
        }

        connection.close();
        redisClient.shutdown();
        return players;

    }
    public void addSession(Session session) {
        System.out.println("in add session");

        sessions.add(session);
        for (GameModel device : games) {
            JsonObject addMessage = createAddMessage(device);
            sendToSession(session, addMessage);
        }
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public List<GameModel> getGames() {
        return new ArrayList<>(games);
    }

    public void addGame(GameModel game) {
        System.out.println("in add game");

        Integer id = new Integer(game.getGameId());
        game.setGameId(id.toString());
        games.add(game);
//        gameId++;
        JsonObject addMessage = createAddMessage(game);
        sendToAllConnectedSessions(addMessage);
    }

    public void removeGame(int id, int token) {
        Integer idInt = new Integer(id);
        GameModel device = getGameById(idInt.toString());
        removeFromRedis(token);
//        GameModel device = getGameById(id);
        if (device != null) {
            games.remove(device);
            JsonProvider provider = JsonProvider.provider();
            JsonObject removeMessage = provider.createObjectBuilder()
                    .add("action", "remove")
                    .add("id", id)
                    .add("token", token)
                    .build();
            sendToAllConnectedSessions(removeMessage);
        }
    }

    public void toggleGame(int id, int token) {
        JsonProvider provider = JsonProvider.provider();
        Integer idInt = new Integer(id);
        GameModel device = getGameById(idInt.toString());
        if (device != null) {
           List<Integer> win_lose =  getResults(idInt.toString());


            JsonObject updateDevMessage = provider.createObjectBuilder()
                    .add("action", "toggle")
                    .add("id", device.getGameId())
                    .add("status", device.getStatus())
                    .add("winner", win_lose.get(0))
                    .add("loser", win_lose.get(1))
                    .build();
            sendToAllConnectedSessions(updateDevMessage);
        }
    }

    private GameModel getGameById(String id) {
        for (GameModel device : games) {
            if (device.getGameId() == id) {
                return device;
            }
        }
        return null;
    }

    private JsonObject createAddMessage(GameModel game) {
        JsonProvider provider = JsonProvider.provider();

        JsonObject addMessage = provider.createObjectBuilder()
                .add("action", "add")
                .add("id", game.getGameId())
                .add("status", game.getStatus())
//                .add("description", device.getDescription())
                .build();
        return addMessage;
    }

    private void sendToAllConnectedSessions(JsonObject message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private void sendToSession(Session session, JsonObject message) {
        try {
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException ex) {
            sessions.remove(session);
//            Logger.getLogger(GameSessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}