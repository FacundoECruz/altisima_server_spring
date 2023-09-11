package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.*;
import com.facu.altisima.model.Game;
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


    List<PlayerRound> generateRoundResults() {
        List<PlayerRound> roundResults = new ArrayList<>();
        for (int j = 0; j < 4; j++) {
            PlayerRound playerRound = new PlayerRound("chueco " + j, 0, 0);
            roundResults.add(playerRound);
        }
        return roundResults;
    }

    Game generateGame() {
        //Generate cardsPerRound
        List<Integer> cardsPerRound = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cardsPerRound.add(i);
        }

        //Generate List of PlayerRound
        List<PlayerRound> roundResults = generateRoundResults();

        //Generate List of players
        List<String> players = new ArrayList<>();
        for (int k = 0; k < roundResults.size(); k++) {
            PlayerRound player = roundResults.get(k);
            String playerName = player.getUsername();
        }

        String gameId = "1";
        String gameDate = "12/12/12";
        Integer currentRound = 2;
        Integer totalRounds = 9;

        return new Game(gameId, gameDate, currentRound, cardsPerRound, players, roundResults, totalRounds);
    }

    @Test
    public void saveGame() throws Exception {
        // ESTE TEST HAY QUE ACOMODARLO.
        Game game = generateGame();
        List<String> players = new ArrayList<>();
        players.add("Migue");
        players.add("Chaky");
        players.add("kevin");

        when(gameService.createGame(players)).thenReturn(game);

        String playersJson = objectMapper.writeValueAsString(players);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playersJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(playersJson));
    }

    @Test
    public void getAllGames() throws Exception {
        List<Game> games = new ArrayList<>();

        when(gameService.getAllGames()).thenReturn((games));
        String expectedRes = "[]";

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedRes));
    }

    @Test
    public void getGameById() throws Exception {
        Game game = generateGame();

        when(gameService.getGame("1")).thenReturn(game);

        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(get("/games/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameJson));
    }

    @Test
    public void deleteGame() throws Exception {
        String successfulMsg = "Successfully deleted";
        Game game = generateGame();

        doNothing().when(gameService).delete("1");
        when(gameService.getGame("1")).thenReturn(game);

        mockMvc.perform(delete("/games/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(successfulMsg));
    }

    @Test
    public void nextRound() throws Exception {
        Game game = generateGame();

        List<PlayerRound> roundResults = generateRoundResults();
        String roundJson = objectMapper.writeValueAsString(roundResults);

        when(gameService.nextRound(game.getId(), roundResults)).thenReturn(game);

        Translate translator = new Translate();
        GameState gameState = translator.toGameState(game);
        String gameStateJson = objectMapper.writeValueAsString(gameState);

        mockMvc.perform(put("/games/1/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(roundJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameStateJson));

    }

    @Test
    public void prevRound() throws Exception {
        Game game = generateGame();

        List<PlayerRound> prevRound = generateRoundResults();
        String prevRoundJson = objectMapper.writeValueAsString(prevRound);

        when(gameService.prevRound(game.getId())).thenReturn(prevRound);

        mockMvc.perform(put("/games/1/prev"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(prevRoundJson));
    }

    @Test
    public void finishGame() throws Exception {
        Game game = generateGame();
        String expectedRes = "Game " + game.getId() + " saved in DB";

        when(gameService.finishGame(game.getId())).thenReturn(game);

        mockMvc.perform(put("/games/1/finish"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedRes));
    }
}
