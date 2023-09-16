package com.facu.altisima.service.utils;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
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

    public List<PlayerRoundDto> generateBidsRound(List<String> players) {
        List<PlayerRoundDto> roundBids = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            PlayerRoundDto playerRoundDto = new PlayerRoundDto(players.get(i), 0, 0);
            roundBids.add(playerRoundDto);
        }
        return roundBids;
    }

    public List<PlayerResultDto> generateCurrentResults(List<String> players) {
        List<PlayerResultDto> currentResults = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            PlayerResultDto playerResultDto = new PlayerResultDto(players.get(i), 0);
            currentResults.add(playerResultDto);
        }
        return currentResults;
    }

    public Game generateGame(List<String> players, Integer totalRounds) {
        String id = uuidGenerator.generate();
        String date = dateFormatter.formatDate(new Date());
        Integer currentRound = 1;
        List<Integer> cardsPerRound = generateCardsPerRound(players.size(), totalRounds);
        List<PlayerRoundDto> lastBidsRound = generateBidsRound(players);
        List<PlayerResultDto> currentResults = generateCurrentResults(players);

        return new Game(id, date, currentRound, cardsPerRound, players, currentResults, lastBidsRound, totalRounds);
    }
}
