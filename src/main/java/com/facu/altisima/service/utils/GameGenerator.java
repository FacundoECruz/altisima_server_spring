package com.facu.altisima.service.utils;

import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.model.Game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameGenerator {

    public GameGenerator() {

    }
    UUIDGenerator uuidGenerator = new UUIDGenerator();
    DateFormatter dateFormatter = new DateFormatter();
    public List<Integer> generateCardsPerRound(Integer playersQty, Integer totalRounds) {
        List<Integer> cardsPerRound = new ArrayList<>();

        Integer cardsInDeck = 40;
        Integer maxCardsPerRound = cardsInDeck / playersQty;

        for(int i = 0; i < totalRounds; i++){
            Integer num = (int) (Math.random() * maxCardsPerRound) + 1;
            cardsPerRound.add(num);
        }
        return cardsPerRound;
    }

    public List<PlayerRound> generateBidsRound(List<String> players) {
        List<PlayerRound> roundBids = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            PlayerRound playerRound = new PlayerRound(players.get(i), 0, 0);
            roundBids.add(playerRound);
        }
        return roundBids;
    }

    public List<PlayerResult> generateCurrentResults(List<String> players) {
        List<PlayerResult> currentResults = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            PlayerResult playerResult = new PlayerResult(players.get(i), 0);
            currentResults.add(playerResult);
        }
        return currentResults;
    }

    public Game generateGame(List<String> players, Integer totalRounds) {
        String id = uuidGenerator.generate();
        String date = dateFormatter.formatDate(new Date());
        Integer currentRound = 1;
        List<Integer> cardsPerRound = generateCardsPerRound(players.size(), totalRounds);
        List<PlayerRound> lastBidsRound = generateBidsRound(players);
        List<PlayerResult> currentResults = generateCurrentResults(players);

        return new Game(id, date, currentRound, cardsPerRound, players, currentResults, lastBidsRound, totalRounds);
    }
}
