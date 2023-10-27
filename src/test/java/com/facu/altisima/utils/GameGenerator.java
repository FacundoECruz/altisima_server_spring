package com.facu.altisima.utils;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.model.Game;

import java.util.*;

public class GameGenerator {

    public GameGenerator() {

    }

    public static final int INSIGNIFICANT_NUMBER = 5;
    public static final int TOTAL_ROUNDS = 9;
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
            PlayerResultDto playerResultDto = new PlayerResultDto(players.get(i), 0, new ArrayList<>());
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
        List<String> playersImgs = generatePlayersImgs(players);

        return new Game(id, date, currentRound, cardsPerRound, players, currentResults, lastBidsRound, totalRounds, playersImgs);
    }

    private static List<String> generatePlayersImgs(List<String> players) {
        List<String> playersImgs = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            String fakeImageUrl = "fakeImageUrl" + i;
            playersImgs.add(fakeImageUrl);
        }
        return playersImgs;
    }
    public Game generateFinished(List<String> players){
        GameGenerator gameGenerator = new GameGenerator();
        Game game = gameGenerator.generate(players, TOTAL_ROUNDS);
        List<PlayerResultDto> finalResults = new ArrayList<>();
        generateFinalResults(players, finalResults);
        game.setCurrentResults(finalResults);
        return game;
    }

    private void generateFinalResults(List<String> playersList, List<PlayerResultDto> finalResults) {
        for (int i = 0; i < playersList.size(); i++) {
            List<Integer> genericHistory = new ArrayList<>();
            for (int j = 0; j < TOTAL_ROUNDS; j++) {
                genericHistory.add(INSIGNIFICANT_NUMBER);
            }
            PlayerResultDto playerResult = new PlayerResultDto(
                    playersList.get(i),
                    (i + 5) * 7,
                    genericHistory);
            finalResults.add(playerResult);
        }
    }
}
