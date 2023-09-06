package com.facu.altisima.controller;

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

    @Test
    public void saveGame() throws Exception {
        Game game = new Game("1", "12/2/23", 1);

        when(gameService.save(game)).thenReturn(game);

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

        when(gameService.getAll()).thenReturn((games));
        String expectedRes = "[]";

        mockMvc.perform(get("/games/api"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedRes));
    }

    @Test
    public void getGameById() throws Exception {
        Game game = new Game("1", "12/2/23", 1);

        when(gameService.get("1")).thenReturn(game);

        String gameJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(get("/games/api/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameJson));
    }

    @Test
    public void deleteGame() throws Exception {
        String successfulMsg = "Successfully deleted";
        Game game = new Game("1", "12/2/23", 1);

        doNothing().when(gameService).delete("1");
        when(gameService.get("1")).thenReturn(game);

        mockMvc.perform(delete("/games/api/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(successfulMsg));
    }

}
