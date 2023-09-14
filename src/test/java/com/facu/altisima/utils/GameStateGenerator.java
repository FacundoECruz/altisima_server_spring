package com.facu.altisima.utils;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.controller.dto.RoundStatus;

import java.util.ArrayList;
import java.util.List;

public class GameStateGenerator {

    public GameState generate(List<PlayerRound> playersRound) {
        Integer currentRound = 2;
        Integer cardsToDeal = 5;
        RoundStatus roundStatus = new RoundStatus(currentRound, cardsToDeal);
        String status = "inProgress";
        List<PlayerResult> roundResults = new ArrayList<>();

        for(int i = 0; i < playersRound.size(); i++) {
            PlayerRound  playerRound = playersRound.get(i);
            PlayerResult playerResult = new PlayerResult(playerRound.getUsername(), 0);
            roundResults.add(playerResult);
        }

        return new GameState(roundStatus, status, roundResults);
    }
}
