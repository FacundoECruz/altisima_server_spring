package com.facu.altisima.service.impl;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerData;
import com.facu.altisima.model.PlayerPerformance;
import com.facu.altisima.service.api.NormalizeDataServiceAPI;

import java.util.ArrayList;
import java.util.List;

public class NormalizeDataService implements NormalizeDataServiceAPI {
    @Override
    public List<PlayerData> normalizeAndUpdatePlayersData(List<Game> allGames) {
        List<Game> completedGames = filterGames(allGames);
        System.out.println(completedGames);
        return null;
    }

    private List<Game> filterGames(List<Game> allGames) {
        List<Game> completedGames = new ArrayList<>();
        for (Game game : allGames) {
            if (game.getCurrentRound() == 10) {
                completedGames.add(game);
            }
        }
        return completedGames;
    }
}
