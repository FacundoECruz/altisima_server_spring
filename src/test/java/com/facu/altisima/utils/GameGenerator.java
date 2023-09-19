package com.facu.altisima.utils;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
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

    public List<PlayerRoundDto> generateRoundBids(List<String> players) {
        List<PlayerRoundDto> roundBids = new ArrayList<>();

        for (int i = 0; i < players.size(); i++) {
            PlayerRoundDto playerRoundDto;
            if(i == 0){
                playerRoundDto = new PlayerRoundDto(players.get(i), 0, 1);
            } else {
                playerRoundDto = new PlayerRoundDto(players.get(i), 0, 0);
            }
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

    public Game generate(List<String> players, Integer totalRounds) {
        String id = idGenerator.generate();
        String date = "18/12/2022 14:55";
        Integer currentRound = 1;
        List<Integer> cardsPerRound = generateCardsPerRound(players.size(), totalRounds);
        List<PlayerRoundDto> lastBidsRound = generateRoundBids(players);
        List<PlayerResultDto> currentResults = generateCurrentResults(players);

        return new Game(id, date, currentRound, cardsPerRound, players, currentResults, lastBidsRound, totalRounds);
    }
}
