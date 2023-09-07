package com.facu.altisima.controller.dto;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameState {
    private RoundStatus round;
    private String status;
    private List<PlayerResult> results;


//    private Map<String, Integer> playersScore;

    public GameState() {


    }
    public GameState(RoundStatus round, String status, List<PlayerResult> results) {
        this.round = round;
        this.status = status;
        this.results = results;
    }


//    public void completeRound(List<PlayerRound> playersResults){
//        if (isValid(playersResults)){
//            playersResults.forEach(player -> {
//                    if (player.getBidsLost() > 0){
//                        Integer currentScore = playersScore.get(player.getUsername());
//                        Integer updatedScore = currentScore - player.getBidsLost();
//                        playersScore.put(player.getUsername(), updatedScore);
//                    } else {
//                        Integer currentScore = playersScore.get(player.getUsername());
//                        Integer updatedScore = currentScore + player.getBid() + BASE_WIN_SCORE;
//                        playersScore.put(player.getUsername(), updatedScore);
//                    }
//            });
//        }
//
//
//    }

    public RoundStatus getRound() {
        return round;
    }

    public void setRound(RoundStatus round) {
        this.round = round;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlayerResult> getResults() {
        return results;
    }

    public void setResults(List<PlayerResult> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Objects.equals(round, gameState.round) && Objects.equals(status, gameState.status) && Objects.equals(results, gameState.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(round, status, results);
    }
}
