package com.facu.altisima.controller.dto;

import com.facu.altisima.model.Game;

import java.util.ArrayList;
import java.util.List;

public class Translate {

    private static final Integer BASE_WIN_SCORE = 5;
    public GameState toGameState(Game game) {
        GameState gameState = new GameState();

        gameState.setStatus("inProgress");
        gameState.setRound(translateRoundStatus(game));
        gameState.setResults(translatePlayerResults(game));

        return gameState;
    }
    private RoundStatus translateRoundStatus(Game game) {
        Integer current = game.currentRound;
        Integer cardsToDeal = game.cardsPerRound.get(current);

        return new RoundStatus(current, cardsToDeal);
    }

    private List<PlayerResult> translatePlayerResults(Game game) {
        List<PlayerResult> playerResults = new ArrayList<>();

        for (PlayerRound playerRound : game.getRoundResults()) {
            PlayerResult playerResult = new PlayerResult();
            playerResult.setUsername(playerRound.getUsername());
            Integer playerBid = playerRound.getBid();
            Integer playerBidsLost = playerRound.getBidsLost();
            if(playerBidsLost == 0) {
                Integer newWinScore = playerBid + BASE_WIN_SCORE; //ACA FALTAN LOS PUNTOS QUE TENIA EN LA RONDA ANTERIOR
                playerResult.setScore(newWinScore);
            } else {
                playerResult.setScore(playerBidsLost);
            }
            playerResults.add(playerResult);
        }
        return playerResults;
    }
}
