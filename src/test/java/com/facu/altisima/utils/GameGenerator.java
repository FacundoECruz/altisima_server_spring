package com.facu.altisima.utils;

import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.model.Game;

import java.util.*;

public class GameGenerator {

    public GameGenerator() {

    }

    FixedIdGenerator idGenerator = new FixedIdGenerator("TestId");

    public List<Integer> generateCardsPerRound(Integer playersQty, Integer totalRounds) {
        List<Integer> cardsPerRound = new ArrayList<>();

        Integer cardsInDeck = 40;
        Integer maxCardsPerRound = cardsInDeck / playersQty;

        for (int i = 0; i < totalRounds; i++) {
            Integer num = (int) (Math.random() * maxCardsPerRound) + 1;
            cardsPerRound.add(num);
        }
        return cardsPerRound;
    }

    public List<PlayerRound> generateRoundResults(List<String> players) {
        List<PlayerRound> roundResults = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            PlayerRound playerRound = new PlayerRound(players.get(i), 0, 0);
            roundResults.add(playerRound);
        }
        return roundResults;
    }

    public Game generate(List<String> players, Integer totalRounds) {
        String id = idGenerator.generate();
        Date date = new Date();
        Integer currentRound = 1;
        List<Integer> cardsPerRound = generateCardsPerRound(players.size(), totalRounds);
        List<PlayerRound> roundResults = generateRoundResults(players);

        return new Game(id, date, currentRound, cardsPerRound, players, roundResults, totalRounds);
    }
}
