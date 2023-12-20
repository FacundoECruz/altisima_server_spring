package com.facu.altisima.service;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerData;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.repository.PlayerDataRepository;
import com.facu.altisima.service.impl.DataService;
import com.facu.altisima.service.impl.NormalizeDataService;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.GameGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class DataServiceTest {
    PlayerDataRepository dataRepository;
    GameRepository gameRepository;
    NormalizeDataService normalizeService;
    GameGenerator gameGenerator = new GameGenerator();
    private DataService dataService;
    PlayerData playerData = new PlayerData("fakeId", "Messi", 234, 10,  0, 90, 82, 8, 70, 70, 96, 36, 11, 2);
    List<PlayerData> playersData = new ArrayList<>();
    List<Game> allGames = new ArrayList<>();
    List<String> players = new ArrayList<>();

    @BeforeEach
    public void setup() {
        players.add("Messi");
        players.add("Mbappe");
        players.add("Dibu");
        players.add("Muani");

        this.dataRepository = mock(PlayerDataRepository.class);
        this.gameRepository = mock(GameRepository.class);
        this.normalizeService = mock(NormalizeDataService.class);
        this.dataService = new DataService(dataRepository, gameRepository, normalizeService);
    }

    @Test
    public void successful_get_player_data() {
        when(dataRepository.findByUsername(playerData.getUsername()))
                .thenReturn(Optional.of(playerData));
        ServiceResult<PlayerData> expectedRes = ServiceResult.success(playerData);
        ServiceResult<PlayerData> res = dataService.get(playerData.getUsername());
        Assertions.assertEquals(expectedRes.getData(), res.getData());
    }

    @Test
    public void unsuccessful_get_player_data() {
        when(dataRepository.findByUsername(playerData.getUsername()))
                .thenReturn(Optional.empty());
        ServiceResult<PlayerData> expectedRes = ServiceResult.error("player data could not be recovered");
        ServiceResult<PlayerData> res = dataService.get(playerData.getUsername());
        Assertions.assertEquals(expectedRes.getErrorMessage(), res.getErrorMessage());
    }

    @Test
    public void successful_get_all_players_data() {
        playersData.add(playerData);
        when(dataRepository.findAll()).thenReturn(playersData);
        ServiceResult<List<PlayerData>> expectedRes = ServiceResult.success(playersData);
        ServiceResult<List<PlayerData>> res = dataService.getAll();
        Assertions.assertEquals(expectedRes.getData(), res.getData());
    }

    @Test
    public void unsuccessful_get_all_players_data() {
        when(dataRepository.findAll()).thenReturn(new ArrayList<>());
        ServiceResult<List<PlayerData>> expectedRes = ServiceResult.error("players data could not be recovered");
        ServiceResult<List<PlayerData>> res = dataService.getAll();
        Assertions.assertEquals(expectedRes.getErrorMessage(), res.getErrorMessage());
    }

    @Test
    public void successful_update_players_data() {
        Game game = gameGenerator.generateFinished(players);
        allGames.add(game);
        when(gameRepository.findAll()).thenReturn(allGames);
        playersData.add(playerData);
        when(normalizeService.normalizeAndUpdatePlayersData(allGames)).thenReturn(playersData);
        when(dataRepository.save(playerData)).thenReturn(playerData);
        ServiceResult<String> expected = ServiceResult.success("Updated players data");
        ServiceResult<String> res = dataService.update();
        Assertions.assertEquals(expected.getData(), res.getData());
    }

    @Test
    public void unsuccessful_update_players_data() {
        when(gameRepository.findAll()).thenReturn(new ArrayList<>());
        ServiceResult<String> expectedRes = ServiceResult.error("Cannot find games");
        ServiceResult<String> res = dataService.update();
        Assertions.assertEquals(expectedRes.getErrorMessage(), res.getErrorMessage());
    }
}
