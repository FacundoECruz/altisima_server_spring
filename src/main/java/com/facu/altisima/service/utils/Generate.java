package com.facu.altisima.service.utils;

import com.facu.altisima.controller.dto.PlayerRound;

import java.util.ArrayList;
import java.util.List;

public class Generate {
    public List<Integer> cardsPerRound(Integer players, Integer totalRounds) {
        List<Integer> cardsPerRound = new ArrayList<>();

        Integer cardsInDeck = 40;
        Integer maxCardsPerRound = cardsInDeck / players;

        for(int i = 0; i < totalRounds; i++){
            Integer num = (int) (Math.random() * maxCardsPerRound) + 1;
            cardsPerRound.add(num);
        }
        return cardsPerRound;
    }

    public List<PlayerRound> roundResults(List<String> players) {
        List<PlayerRound> roundResults = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            PlayerRound playerRound = new PlayerRound(players.get(i), 0, 0);
            roundResults.add(playerRound);
        }
        return roundResults;
    }
}
