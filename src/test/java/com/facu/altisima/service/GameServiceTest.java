package com.facu.altisima.service;

import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.service.utils.DateFormatter;
import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.FixedIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {

    GameRepository gameRepository = mock(GameRepository.class);
    IdGenerator idGenerator = new FixedIdGenerator("TestId");
    private final GameServiceAPI gameService = new GameServiceImpl(gameRepository, idGenerator);
    GameGenerator gameGenerator = new GameGenerator();

    @Test
    public void successfullyCreatedGame() {
        List<String> players = new ArrayList<>();
        players.add("Migue");

        Integer totalRounds = 9;
        Game game = gameGenerator.generate(players, totalRounds);

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        ServiceResult<Game> returnedGame = gameService.createGame(players, totalRounds);

        assertEquals(game, returnedGame.getData());
    }

    @Test
    public void unsuccessfulCreatedGame() {
        List<String> players = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            players.add("Migue" + i);
        }
        Integer totalRounds = 9;
        Game game = gameGenerator.generate(players, totalRounds);

        String expectedErrMsg = "Demasiados jugadores";

        ServiceResult<Game> returnedGame = gameService.createGame(players, totalRounds);

        assertEquals(expectedErrMsg, returnedGame.getErrorMessage());
    }

    @Test
    public void successfulGetAllGames() {
        List<String> players = new ArrayList<>();
        players.add("Migue");
        Integer totalRounds = 9;
        List<Game> games = new ArrayList<>();
        games.add(gameGenerator.generate(players, totalRounds));

        when(gameRepository.findAll()).thenReturn(games);

        ServiceResult<List<Game>> serviceAllGames = gameService.getAllGames();

        assertEquals(games, serviceAllGames.getData());
    }

    @Test
    public void unsuccessfulGetAllGames() {
        String expectedErrMsg = "No se encontraron partidas";
        List<Game> emptyList = new ArrayList<>();

        when(gameRepository.findAll()).thenReturn(emptyList);

        ServiceResult<List<Game>> serviceAllGames = gameService.getAllGames();

        assertEquals(expectedErrMsg, serviceAllGames.getErrorMessage());
    }
}
