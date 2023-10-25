package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.service.utils.ServiceResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HighestRound {
    List<String> scoredTenOrMoreInARound;
    List<Score> highestScoreInARound;
    List<PlayerResultDto> gameResults;

    public HighestRound(List<String> scoredTenOrMoreInARound, List<Score> highestScoreInARound, List<PlayerResultDto> gameResults) {
        this.scoredTenOrMoreInARound = scoredTenOrMoreInARound;
        this.highestScoreInARound = highestScoreInARound;
        this.gameResults = gameResults;
    }

    public ServiceResult<List<String>> checkTenOrMoreInARound() {
        boolean hadChanges = false;
        for (int i = 0; i < gameResults.size(); i++) {
            List<Integer> playerRoundsResult = gameResults.get(i).getHistory();
            for (Integer integer : playerRoundsResult) {
                if (integer >= 10) {
                    scoredTenOrMoreInARound.add(gameResults.get(i).getUsername());
                    hadChanges = true;
                }
            }
        }
        if (hadChanges)
            return ServiceResult.success(scoredTenOrMoreInARound);
        else
            return ServiceResult.error("No hubo jugadores con +10 por ronda");
    }

    public ServiceResult<List<Score>> checkHighestRound() {
        boolean hadChanges = false;
        for (int i = 0; i < gameResults.size(); i++) {
            List<Integer> playerRoundsResult = gameResults.get(i).getHistory();
            for (Integer integer : playerRoundsResult) {
                if (Objects.equals(integer, highestScoreInARound.get(0).getScore())) {
                    Score playerWithRecord = new Score(
                            gameResults.get(i).getUsername(),
                            integer);
                    highestScoreInARound.add(playerWithRecord);
                    hadChanges = true;
                } else if (integer > highestScoreInARound.get(0).getScore()) {
                    List<Score> container = new ArrayList<>();
                    Score playerWithRecord = new Score(
                            gameResults.get(i).getUsername(),
                            integer);
                    container.add(playerWithRecord);
                    highestScoreInARound = container;
                    hadChanges = true;
                }
            }

        }
        if (hadChanges)
            return ServiceResult.success(highestScoreInARound);
        else
            return ServiceResult.error("No hubo jugadores con +10 por ronda");

    }
}