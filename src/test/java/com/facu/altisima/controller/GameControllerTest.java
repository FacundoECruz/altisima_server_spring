package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.GameStateGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
    GameGenerator gameGenerator = new GameGenerator();

    GameStateGenerator gameStateGenerator = new GameStateGenerator();

    private List<String> players;
    Integer totalRounds = 9;

    @BeforeEach
    public void setup() {
        players = new ArrayList<>();
        players.add("Migue");
    }

    @Test
    public void saveGame() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        ServiceResult<Game> serviceGame = ServiceResult.success(game);

        when(gameService.createGame(players, totalRounds)).thenReturn(serviceGame);

        GameRequestDto gameRequestDto = new GameRequestDto();
        gameRequestDto.setPlayers(players);
        gameRequestDto.setTotalRounds(totalRounds);

        String gameRequestJson = objectMapper.writeValueAsString(gameRequestDto);
        String gameJson = objectMapper.writeValueAsString(serviceGame.getData());

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameJson));
    }

    @Test
    public void tooManyPlayersForTheGame() throws Exception {
        String expectedErrMsg = "Demasiados jugadores";
        ServiceResult<Game> serviceGame = ServiceResult.error(expectedErrMsg);

        when(gameService.createGame(players, totalRounds)).thenReturn(serviceGame);

        GameRequestDto gameRequestDto = new GameRequestDto();
        gameRequestDto.setPlayers(players);
        gameRequestDto.setTotalRounds(totalRounds);

        String gameRequestJson = objectMapper.writeValueAsString(gameRequestDto);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void getAllGames() throws Exception {
        List<Game> games = new ArrayList<>();
        games.add(gameGenerator.generate(players, totalRounds));

        ServiceResult<List<Game>> serviceGames = ServiceResult.success(games);

        when(gameService.getAllGames()).thenReturn((serviceGames));
        String expectedRes = objectMapper.writeValueAsString(serviceGames.getData());

        mockMvc.perform(get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedRes));
    }

    @Test
    public void unsuccessfulGetAllGames() throws Exception {
        String expectedErrMsg = "No se encontraron partidas";
        ServiceResult<List<Game>> serviceGames = ServiceResult.error(expectedErrMsg);

        when(gameService.getAllGames()).thenReturn((serviceGames));

        mockMvc.perform(get("/games"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }


    @Test
    public void successfulGetGameById() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);

        ServiceResult<Game> expectedGame = ServiceResult.success(game);

        when(gameService.getGame(game.getId())).thenReturn(expectedGame);

        String urlTemplate = "/games/" + game.getId();
        String expectedJson = objectMapper.writeValueAsString(game);

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedJson));
    }

    @Test
    public void gameWithGivenIdNotFound() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);

        String gameNotFoundMsg = "No se encontro partida con ese id";
        ServiceResult<Game> gameNotFound = ServiceResult.error(gameNotFoundMsg);

        when(gameService.getGame(game.getId())).thenReturn(gameNotFound);
        String urlTemplate = "/games/" + game.getId();

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(gameNotFoundMsg));
    }

    @Test
    public void deleteGame() throws Exception {
        String successfulDeleteMsg = "Exitosamente borrado";
        Game game = gameGenerator.generate(players, totalRounds);
        ServiceResult<Game> gameServiceResult = ServiceResult.success(game);

        when(gameService.getGame(game.getId())).thenReturn(gameServiceResult);
        doNothing().when(gameService).delete(game.getId());

        String urlTemplate = "/games/" + game.getId();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().string(successfulDeleteMsg));
    }

    @Test
    public void gameToDeleteNotFound() throws Exception {
        String unsuccessfulDeleteMsg = "No se encontro la partida";
        Game game = gameGenerator.generate(players, totalRounds);
        ServiceResult<Game> gameServiceResult = ServiceResult.error("No se encontro partida con ese id");

        when(gameService.getGame(game.getId())).thenReturn(gameServiceResult);

        String urlTemplate = "/games/" + game.getId();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(unsuccessfulDeleteMsg));
    }

    @Test
    public void successfulNextRound() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        List<PlayerRound> playersRound = gameGenerator.generateRoundResults(players);

        String urlTemplate = "/games/" + game.getId() + "/next";
        String playersRoundJson = objectMapper.writeValueAsString(playersRound);

        GameState gameState = gameStateGenerator.generate(playersRound);
        String gameStateJson = objectMapper.writeValueAsString(gameState);

        ServiceResult<GameState> gameStateResult = ServiceResult.success(gameState);
        when(gameService.nextRound(game.getId(), playersRound)).thenReturn(gameStateResult);

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playersRoundJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameStateJson));

    }
}