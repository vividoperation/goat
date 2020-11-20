package com.usc.csci401.testgameweb.params;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameParam {

   @NotNull
    private Integer player1;

   @NotNull
    private Integer player2;
}