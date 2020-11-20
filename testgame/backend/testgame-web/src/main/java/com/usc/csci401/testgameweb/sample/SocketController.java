package com.usc.csci401.testgameweb.sample;
import com.usc.csci401.testgameweb.utils.*;
import com.usc.csci401.testgameweb.http.*;
import com.usc.csci401.testgameweb.sample.MessageBean;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.ArrayList;
import com.lambdaworks.redis.*;
import java.util.Map;
import java.util.HashMap;


@Controller
public class SocketController {

    private List<String> previous=new ArrayList<String>();

    @Value("${redis.url}")
    private String redisURL;

    @Value("${goat.backend.endpoint}")
    private String goatBackendEndpoint;

    private String winner = "-1";
    private String loser = "-1";

    @MessageMapping("/user-all")
    @SendTo("/topic/user")
    public MessageBean send(@Payload MessageBean message) throws Exception {
        System.out.println(message.getMessage());

        // if (message.getMessage().equals("close"))
        // {

        //     RedisClient redisClient = new RedisClient(
        //             RedisURI.create(redisURL));
        //     RedisConnection<String, String> connection = redisClient.connect();

        //     List<String> list = connection.keys("*");
        //     System.out.println(list.size());
        //     Integer gameToRemove = -1;
        //     System.out.println("message.name");
        //     System.out.println(message.getName());
        //     boolean findWinner = false;
        //     boolean findLoser = false;


        //     if(!message.getName().equals(winner))
        //     {
        //         loser = message.getName();
        //         findWinner = true;
        //     }
        //     for (int i = 0; i < list.size(); i++)
        //     {
        //         System.out.println("list.get(i)");
        //         System.out.println(list.get(i));
        //         if(list.get(i).equals(message.getName())){
        //             System.out.println("check the game");
        //             gameToRemove = Integer.parseInt(connection.get(list.get(i)));
        //         }
        //     }

        //     System.out.println(gameToRemove);
        //     for (int i = 0; i < list.size(); i++)
        //     {
        //         if( Integer.parseInt(connection.get(list.get(i))) == gameToRemove){
        //             if(findWinner == true && !list.get(i).equals(loser))
        //             {
        //                 winner = list.get(i);
        //             }
        //            connection.del(list.get(i));
        //         }
        //     }

            // list = connection.keys("*");
            // System.out.println(list.size());

            // connection.close();
            // redisClient.shutdown();
            // previous.clear();

            // message.setName("");
            // message.setMessage("");
            // System.out.println("Winner: " + winner);

            // Map<String, Object> param = Maps.newHashMap();

            // param.put("winner", Integer.parseInt(winner));

            // String backendUrl = "http://goat-backend:8080/games/reportwinner";
            // String res;
            // try {
            //     res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.OK);
            // } catch (Exception e) {
            //     exception = true;
            //     throw new GameException("Error connecting to goat backend");
            // }

        //     if(res == null){
        //         exception = true;
        //         throw new GameException("Unexpected Return from goat backend");
        //     }

        //     winner = "-1";
        //     loser = "-1";
        // }


        if (message.getMessage().equals("rock"))
        {
            System.out.println("here1");

            if (previous.isEmpty())
            {
                System.out.println("here2");

                previous.add("rock");
                message.setMessage("waiting");
            }
            //check if there is a paper scissors or rock in queue
            //if rock
            else if(previous.size() == 1)
            {
                System.out.println("here3");

                if(previous.get(0).equals("rock"))
                {
                    message.setMessage("draw");
                }
                else if(previous.get(0).equals( "paper"))
                {
                    message.setMessage("loser");
                    loser = message.getName();
                    reportWinner(message);
                }
                else if(previous.get(0).equals( "scissors"))
                {
                    winner = message.getName();
                    message.setMessage("winner");
                    reportWinner(message);
                }
                previous.clear();
//                if (message.getMessage().equals("winner"))
//                {
//                    previous.add("loser");
//                }
//                if (message.getMessage().equals("loser"))
//                {
//                    previous.add("winner");
//                }

            }


        }

        else if (message.getMessage().equals("paper"))
        {
            if (previous.isEmpty())
            {
                previous.add("paper");
                message.setMessage("waiting");
            }
            //check if there is a paper scissors or rock in queue
            //if rock
            else if(previous.size() == 1)
            {
                if(previous.get(0).equals("paper"))
                {
                    message.setMessage("draw");
                }
                else if(previous.get(0).equals("scissors"))
                {
                    message.setMessage("loser");
                    loser = message.getName();
                    reportWinner(message);
                }
                else if(previous.get(0).equals("rock"))
                {
                    message.setMessage("winner");
                    winner = message.getName();
                    reportWinner(message);
                }
                previous.clear();
//                if (message.getMessage().equals("winner"))
//                {
//                    previous.add("loser");
//                }
//                if (message.getMessage().equals("loser"))
//                {
//                    previous.add("winner");
//                }

            }


        }

        else if (message.getMessage().equals("scissors"))
        {
            if (previous.isEmpty())
            {
                previous.add("scissors");
                message.setMessage("waiting");
            }
            //check if there is a paper scissors or rock in queue
            //if rock
            else if(previous.size() == 1)
            {
                if(previous.get(0).equals("scissors"))
                {
                    message.setMessage("draw");
                }
                else if(previous.get(0).equals( "rock"))
                {
                    message.setMessage("loser");
                    loser = message.getName();
                    reportWinner(message);
                }
                else if(previous.get(0).equals("paper"))
                {
                    message.setMessage("winner");
                    winner = message.getName();
                    reportWinner(message);
                }
                previous.clear();



            }


        }
        else{
            if(previous.size() == 1)
            {
                message.setMessage(previous.get(0));
                previous.clear();

            }
        }

        // list = connection.keys("*");
        // System.out.println(list.size());

        // connection.close();
        // redisClient.shutdown();
        // previous.clear();

        // message.setName("");
        // message.setMessage("");
        // System.out.println("Winner: " + winner);

        // Map<String, Object> param = Maps.newHashMap();

        // param.put("winner", Integer.parseInt(winner));

        // String backendUrl = "http://goat-backend:8080/games/reportwinner";
        // String res;
        // try {
        //     res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.OK);
        // } catch (Exception e) {
        //     exception = true;
        //     throw new GameException("Error connecting to goat backend");
        // }

        // if(res == null){
        //     exception = true;
        //     throw new GameException("Unexpected Return from goat backend");
        // }

        // winner = "-1";
        // loser = "-1";


//        message.setMessage("HIIIIIII");

    return message;
}

    void reportWinner(MessageBean message) throws Exception {
        RedisClient redisClient = new RedisClient(
        RedisURI.create(redisURL));
        RedisConnection<String, String> connection = redisClient.connect();

        List<String> list = connection.keys("*");
        System.out.println(list.size());
        Integer gameToRemove = -1;
        System.out.println("message.name");
        System.out.println(message.getName());
        boolean findWinner = false;
        boolean findLoser = false;


        if(!message.getName().equals(winner))
        {
            loser = message.getName();
            findWinner = true;
        }
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println("list.get(i)");
            System.out.println(list.get(i));
            if(list.get(i).equals(message.getName())){
                System.out.println("check the game");
                gameToRemove = Integer.parseInt(connection.get(list.get(i)));
            }
        }

        System.out.println(gameToRemove);
        for (int i = 0; i < list.size(); i++)
        {
        if( Integer.parseInt(connection.get(list.get(i))) == gameToRemove){
        if(findWinner == true && !list.get(i).equals(loser))
        {
            winner = list.get(i);
        }
        connection.del(list.get(i));
        }
        }

        list = connection.keys("*");
        System.out.println(list.size());

        connection.close();
        redisClient.shutdown();
        previous.clear();

        // message.setName("");
        // message.setMessage("");
        System.out.println("Winner: " + winner);

        Map<String, Object> param = new HashMap<String, Object>();

        param.put("winner", Integer.parseInt(winner));

        String backendUrl = this.goatBackendEndpoint + "/games/reportwinner";
        String res;
        try {
            res = HttpUtils.doPost(backendUrl, JsonUtils.toJson(param), HttpStatus.OK);
            } catch (Exception e) {
                throw new Exception("Error connecting to goat backend");
        }

        if(res == null){
            throw new Exception("Unexpected Return from goat backend");
        }

        winner = "-1";
        loser = "-1";
        }

}