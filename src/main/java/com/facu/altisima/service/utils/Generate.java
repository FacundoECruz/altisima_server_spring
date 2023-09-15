package com.facu.altisima.service.utils;

import com.facu.altisima.controller.dto.PlayerResult;
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

    public List<PlayerRound> roundBids(List<String> players) {
        List<PlayerRound> roundBids = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            PlayerRound playerRound = new PlayerRound(players.get(i), 0, 0);
            roundBids.add(playerRound);
        }
        return roundBids;
    }

    public List<PlayerResult> roundResults(List<String> players) {
        List<PlayerResult> playersResult = new ArrayList<>();

        for(int i = 0; i < players.size(); i++) {
            PlayerResult playerResult = new PlayerResult(players.get(i), 0);
            playersResult.add(playerResult);
        }
        return playersResult;
    }
}
