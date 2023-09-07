package com.facu.altisima.model;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.controller.dto.RoundStatus;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Game {
    @Id
    private String id;
    private String date;
    private Integer currentRound;

    private List<Integer> cardsPerRound;

    private List<Player> players;

    private List<PlayerRound> roundResults;

    private Integer totalRounds;

    private static final Integer BASE_WIN_SCORE = 5;

    public Game() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public List<Integer> getCardsPerRound() {
        return cardsPerRound;
    }

    public void setCardsPerRound(List<Integer> cardsPerRound) {
        this.cardsPerRound = cardsPerRound;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<PlayerRound> getRoundResults() {
        return roundResults;
    }

    public void setRoundResults(List<PlayerRound> roundResults) {
        this.roundResults = roundResults;
    }

    public Integer getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(Integer totalRounds) {
        this.totalRounds = totalRounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(date, game.date) && Objects.equals(currentRound, game.currentRound) && Objects.equals(cardsPerRound, game.cardsPerRound) && Objects.equals(players, game.players) && Objects.equals(roundResults, game.roundResults) && Objects.equals(totalRounds, game.totalRounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, currentRound, cardsPerRound, players, roundResults, totalRounds);
    }

    public Game(String id, String date, Integer currentRound, List<Integer> cardsPerRound, List<Player> players, List<PlayerRound> roundResults, Integer totalRounds) {
        this.id = id;
        this.date = date;
        this.currentRound = currentRound;
        this.cardsPerRound = cardsPerRound;
        this.players = players;
        this.roundResults = roundResults;
        this.totalRounds = totalRounds;
    }

    public GameState toGameState() {
        GameState gameState = new GameState();

        gameState.setStatus("inProgress");
        gameState.setRound(translateRoundStatus());
        gameState.setResults(translatePlayerResults());

        return gameState;
    }
    private RoundStatus translateRoundStatus() {
        Integer current = currentRound;
        Integer cardsToDeal = cardsPerRound.get(current);

        return new RoundStatus(current, cardsToDeal);
    }

    private List<PlayerResult> translatePlayerResults() {
        List<PlayerResult> playerResults = new ArrayList<>();

        for (PlayerRound playerRound : roundResults) {
            PlayerResult playerResult = new PlayerResult();
            playerResult.setUsername(playerRound.getUsername());
            Integer playerBid = playerRound.getBid();
            Integer playerBidsLost = playerRound.getBidsLost();
            if(playerBidsLost == 0) {
                Integer newWinScore = playerBid + BASE_WIN_SCORE; //ACA FALTAN LOS PUNTOS QUE TENIA EN LA RONDA ANTERIOR
                playerResult.setScore(newWinScore);
            } else {
                Integer newLostScore = playerBidsLost;
                playerResult.setScore(newLostScore);
            }
            playerResults.add(playerResult);
        }
        return playerResults;
    }

}
