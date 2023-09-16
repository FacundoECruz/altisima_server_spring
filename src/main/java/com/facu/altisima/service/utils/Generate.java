package com.facu.altisima.service.utils;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;

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

    public List<PlayerRoundDto> roundBids(List<String> players) {
        List<PlayerRoundDto> roundBids = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            PlayerRoundDto playerRoundDto = new PlayerRoundDto(players.get(i), 0, 0);
            roundBids.add(playerRoundDto);
        }
        return roundBids;
    }

    public List<PlayerResultDto> roundResults(List<String> players) {
        List<PlayerResultDto> playersResult = new ArrayList<>();

        for(int i = 0; i < players.size(); i++) {
            PlayerResultDto playerResultDto = new PlayerResultDto(players.get(i), 0);
            playersResult.add(playerResultDto);
        }
        return playersResult;
    }
}
