package com.facu.altisima.service;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerData;
import com.facu.altisima.model.PlayerPerformance;
import com.facu.altisima.service.impl.NormalizeDataService;
import com.facu.altisima.utils.GameGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class NormalizeServiceTest {
    GameGenerator generator = new GameGenerator();
    NormalizeDataService dataService;
    List<String> players = new ArrayList<>();
    PlayerData playerData = new PlayerData("fakeId", "Messi", 234, 10, 1, 0, 90, 82, 8, 70, 70, 96, 36, 11, 2);
    List<PlayerData> playersData = new ArrayList<>();
    List<Game> allGames = new ArrayList<>();

    @BeforeEach
    public void setup() {
        players.add("Messi");
        players.add("Cristiano");
        players.add("Mbappe");
        players.add("Dibu");

        Game game1 = generator.generateFinished(players);
        Game game2 = generator.generateFinished(players);
        game2.setCurrentRound(5);
        allGames.add(game1);
        allGames.add(game2);
        playersData.add(playerData);
        this.dataService = new NormalizeDataService();
    }

    @Test
    public void should_return_players_performances() {
        List<PlayerData> expectedRes = playersData;
        List<PlayerData> res = dataService.normalizeAndUpdatePlayersData(allGames);
        Assertions.assertEquals(expectedRes, res);
    }
}
