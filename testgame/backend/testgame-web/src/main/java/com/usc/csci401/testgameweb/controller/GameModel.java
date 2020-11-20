package com.usc.csci401.testgameweb.controller;

import com.lambdaworks.redis.*;
import com.usc.csci401.testgameweb.utils.HttpResult;
import com.usc.csci401.testgameweb.utils.JsonUtils;
import com.usc.csci401.testgameweb.params.TestParam;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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


public class GameModel {

    private String token;
    private String gameId;
    private String status;
//    private String type;
//    private String description;

    public GameModel() {
    }

    public String getToken() {
        return token;
    }

    public String getGameId() {
        return gameId;
    }

    public String getStatus() {
        return status;
    }



    public void setToken(String token) {
        this.token = token;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }


    public void setStatus(String status) {
        this.status = status;
    }


}