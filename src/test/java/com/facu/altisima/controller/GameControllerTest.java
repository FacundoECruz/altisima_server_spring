package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.RoundStatus;
import com.facu.altisima.model.Game;
import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {
    @MockBean
    GameServiceImpl gameService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void saveGame() throws Exception {
        List<Integer> cardsPerRound = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        PlayerRound chuecoRound = new PlayerRound("chueco", 0, 0);
        List<PlayerRound> roundResults = new ArrayList<>();
        roundResults.add(chuecoRound);
        Game game = new Game("1", "12/2/23", 2, cardsPerRound, players, roundResults, 9);

        when(gameService.saveGame(game)).thenReturn(game);

        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(post("/games/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameJson));
    }

    @Test
    public void getAllGames() throws Exception {
        List<Game> games = new ArrayList<>();

        when(gameService.getAllGames()).thenReturn((games));
        String expectedRes = "[]";

        mockMvc.perform(get("/games/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedRes));
    }

    @Test
    public void getGameById() throws Exception {
        List<Integer> cardsPerRound = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        PlayerRound chuecoRound = new PlayerRound("chueco", 0, 0);
        List<PlayerRound> roundResults = new ArrayList<>();
        roundResults.add(chuecoRound);
        Game game = new Game("1", "12/2/23", 2, cardsPerRound, players, roundResults, 9);

        when(gameService.getGame("1")).thenReturn(game);

        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(get("/games/api/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameJson));
    }

    @Test
    public void deleteGame() throws Exception {
        String successfulMsg = "Successfully deleted";
        List<Integer> cardsPerRound = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        PlayerRound chuecoRound = new PlayerRound("chueco", 0, 0);
        List<PlayerRound> roundResults = new ArrayList<>();
        roundResults.add(chuecoRound);
        Game game = new Game("1", "12/2/23", 2, cardsPerRound, players, roundResults, 9);

        doNothing().when(gameService).delete("1");
        when(gameService.getGame("1")).thenReturn(game);

        mockMvc.perform(delete("/games/api/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(successfulMsg));
    }

    @Test
    public void nextRound() throws Exception {
        PlayerResult chuecoResults = new PlayerResult("chueco", 5);
        List<PlayerResult> scores = new ArrayList<>();
        scores.add(chuecoResults);

        PlayerRound chuecoRound = new PlayerRound("chueco", 0, 0);
        List<PlayerRound> roundResults = new ArrayList<>();
        roundResults.add(chuecoRound);
        String roundJson = objectMapper.writeValueAsString(roundResults);

        String gameId = "1";
        String status = "inProgress";
        RoundStatus round = new RoundStatus(2, 2);

        GameState gameState = new GameState(round, status, scores);
        String gameStateJson = objectMapper.writeValueAsString(gameState);

        List<Integer> cardsPerRound = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cardsPerRound.add(i);
        }

        Game game = new Game("1", "12/2/23", 2, cardsPerRound, players, roundResults, 9);

        when(gameService.nextRound(gameId)).thenReturn(game);

        mockMvc.perform(put("/games/api/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roundJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameStateJson));

    }
}
