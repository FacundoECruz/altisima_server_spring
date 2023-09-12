package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.service.utils.GameGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    GameGenerator gameGenerator = new GameGenerator();

    @Test
    public void saveGame() throws Exception {
        List<String> players = new ArrayList<>();
        players.add("Migue");
        players.add("Chaky");
        players.add("kevin");
        Integer totalRounds = 9;
        Game game = new Game(UUID.randomUUID(), new Date(), 1, gameGenerator.generateCardsPerRound(players.size(), totalRounds), players, gameGenerator.generateRoundResults(players), totalRounds);
        ServiceResult<Game> serviceGame = ServiceResult.success(game);

        when(gameService.createGame(players, totalRounds)).thenReturn(serviceGame);

        String playersJson = objectMapper.writeValueAsString(players);
        String gameJson = objectMapper.writeValueAsString(serviceGame.getData());

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playersJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameJson));
    }
//
//    @Test
//    public void getAllGames() throws Exception {
//        List<Game> games = new ArrayList<>();
//
//        when(gameService.getAllGames()).thenReturn((games));
//        String expectedRes = "[]";
//
//        mockMvc.perform(get("/games"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(expectedRes));
//    }
//
//    @Test
//    public void getGameById() throws Exception {
//        Game game = generateGame();
//
//        when(gameService.getGame("1")).thenReturn(game);
//
//        String gameJson = objectMapper.writeValueAsString(game);
//
//        mockMvc.perform(get("/games/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(gameJson));
//    }
//
//    @Test
//    public void deleteGame() throws Exception {
//        String successfulMsg = "Successfully deleted";
//        Game game = generateGame();
//
//        doNothing().when(gameService).delete("1");
//        when(gameService.getGame("1")).thenReturn(game);
//
//        mockMvc.perform(delete("/games/1"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(successfulMsg));
//    }
//
//    @Test
//    public void nextRound() throws Exception {
//        Game game = generateGame();
//
//        List<PlayerRound> roundResults = generateRoundResults();
//        String roundJson = objectMapper.writeValueAsString(roundResults);
//
//        when(gameService.nextRound(game.getId(), roundResults)).thenReturn(game);
//
//        Translate translator = new Translate();
//        GameState gameState = translator.toGameState(game);
//        String gameStateJson = objectMapper.writeValueAsString(gameState);
//
//        mockMvc.perform(put("/games/1/next")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(roundJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(gameStateJson));
//
//    }
//
//    @Test
//    public void prevRound() throws Exception {
//        Game game = generateGame();
//
//        List<PlayerRound> prevRound = generateRoundResults();
//        String prevRoundJson = objectMapper.writeValueAsString(prevRound);
//
//        when(gameService.prevRound(game.getId())).thenReturn(prevRound);
//
//        mockMvc.perform(put("/games/1/prev"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string(prevRoundJson));
//    }
//
//    @Test
//    public void finishGame() throws Exception {
//        Game game = generateGame();
//        String expectedRes = "Game " + game.getId() + " saved in DB";
//
//        when(gameService.finishGame(game.getId())).thenReturn(game);
//
//        mockMvc.perform(put("/games/1/finish"))
//                .andExpect(status().isOk())
//                .andExpect(content().string(expectedRes));
//    }
}
