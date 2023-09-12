package com.facu.altisima.service;

import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.GameGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameServiceAPI gameService;

    @MockBean
    GameRepository gameRepository;

    GameGenerator gameGenerator = new GameGenerator();

    @Test
    public void successfullyCreatedGame() {
        List<String> players = new ArrayList<>();
        players.add("Migue");
        players.add("Chaky");
        players.add("kevin");
        Integer totalRounds = 9;
        Game game = new Game(UUID.randomUUID(), new Date(), 1, gameGenerator.generateCardsPerRound(players.size(), totalRounds), players, gameGenerator.generateRoundResults(players), totalRounds);

        when(gameRepository.save(any(Game.class))).thenReturn(game);

        ServiceResult<Game> returnedGame = gameService.createGame(players, totalRounds);

        assertEquals(game, returnedGame.getData());
    }

}
