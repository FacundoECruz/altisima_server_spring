package com.facu.altisima.controller.dto;

import com.facu.altisima.model.Game;

import java.util.List;
import java.util.Objects;

public class GameStateDto {
    private RoundStatusDto round;
    private String status;
    private List<PlayerResultDto> results;


//    private Map<String, Integer> playersScore;

    public GameStateDto() {


    }
    public GameStateDto(Game game) {
        RoundStatusDto round = new RoundStatusDto(game.getCurrentRound(), game.getCardsPerRound().get(game.getCurrentRound() - 1));
        Integer lastRoundIndex = game.getRoundResults().size();
        List<PlayerResultDto> results = game.getCurrentResults();

        this.round = round;
        this.status = checkStatus(game);
        this.results = results;
    }

    private String checkStatus(Game game) {
        if(game.getCurrentRound() >= game.getTotalRounds()){
            return "finished";
        } else {
            return "inProgress";
        }
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

    public RoundStatusDto getRound() {
        return round;
    }

    public void setRound(RoundStatusDto round) {
        this.round = round;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlayerResultDto> getResults() {
        return results;
    }

    public void setResults(List<PlayerResultDto> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStateDto gameStateDto = (GameStateDto) o;
        return Objects.equals(round, gameStateDto.round) && Objects.equals(status, gameStateDto.status) && Objects.equals(results, gameStateDto.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(round, status, results);
    }
}
