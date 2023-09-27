package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

    private List<String> players;
    Integer totalRounds = 9;
    Game game;
    ServiceResult<Game> succeedGame;
    String path = "/games";
    @BeforeEach
    public void setup() {
        players = new ArrayList<>();
        players.add("Migue");
        game = gameGenerator.generate(players, totalRounds);
        succeedGame = ServiceResult.success(game);
    }

    @Test
    public void saveGame() throws Exception {
        when(gameService.createGame(players, totalRounds)).thenReturn(succeedGame);
        GameRequestDto gameRequestDto = new GameRequestDto(players, totalRounds);
        GameCreatedDto gameCreatedDto = new GameCreatedDto(game);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(gameRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(gameCreatedDto)));
    }

    @Test
    public void tooManyPlayersForTheGame() throws Exception {
        String expectedErrMsg = "Demasiados jugadores";
        ServiceResult<Game> serviceGame = ServiceResult.error(expectedErrMsg);
        when(gameService.createGame(players, totalRounds)).thenReturn(serviceGame);
        GameRequestDto gameRequestDto = new GameRequestDto(players, totalRounds);

        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(gameRequestDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void getAllGames() throws Exception {
        List<Game> games = new ArrayList<>();
        games.add(game);
        ServiceResult<List<Game>> serviceGames = ServiceResult.success(games);
        when(gameService.getAllGames()).thenReturn((serviceGames));
        String expectedRes = objectMapper.writeValueAsString(serviceGames.getData());

        mockMvc.perform(get(path))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(expectedRes));
    }

    @Test
    public void unsuccessfulGetAllGames() throws Exception {
        String expectedErrMsg = "No se encontraron partidas";
        ServiceResult<List<Game>> serviceGames = ServiceResult.error(expectedErrMsg);
        when(gameService.getAllGames()).thenReturn((serviceGames));

        mockMvc.perform(get(path))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulGetGameById() throws Exception {
        when(gameService.getGame(game.getId())).thenReturn(succeedGame);

        String urlTemplate = path + "/" + game.getId();

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(game)));
    }

    @Test
    public void gameWithGivenIdNotFound() throws Exception {
        String gameNotFoundMsg = "No se encontro partida con ese id";
        ServiceResult<Game> gameNotFound = ServiceResult.error(gameNotFoundMsg);

        when(gameService.getGame(game.getId())).thenReturn(gameNotFound);
        String urlTemplate = path + "/" + game.getId();

        mockMvc.perform(get(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(gameNotFoundMsg));
    }

    @Test
    public void deleteGame() throws Exception {
        String successfulDeleteMsg = "Exitosamente borrado";
        when(gameService.delete(game.getId())).thenReturn(ServiceResult.success(successfulDeleteMsg));
        String urlTemplate = path + "/" + game.getId();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().string(successfulDeleteMsg));
    }

    @Test
    public void gameToDeleteNotFound() throws Exception {
        String unsuccessfulDeleteMsg = "No se encontro la partida";
        when(gameService.delete(game.getId())).thenReturn(ServiceResult.error(unsuccessfulDeleteMsg));
        String urlTemplate = path + "/" + game.getId();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(unsuccessfulDeleteMsg));
    }

    @Test
    public void successfulNextRound() throws Exception {
        List<PlayerRoundDto> round = gameGenerator.generateRoundBids(players);
        String urlTemplate = path + "/" + game.getId() + "/next";
        when(gameService.nextRound(game.getId(), round)).thenReturn(succeedGame);
        GameStateDto gameStateDto = new GameStateDto(succeedGame.getData());


        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(round)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(gameStateDto)));
    }

    @Test
    public void unsuccessfulNextRound() throws Exception {
        List<PlayerRoundDto> round = gameGenerator.generateRoundBids(players);
        String playersRoundJson = objectMapper.writeValueAsString(round);

        String urlTemplate = path + "/" + game.getId() + "/next";
        String expectedErrMsg = "Algun mensaje de error";

        ServiceResult<Game> gameResult = ServiceResult.error(expectedErrMsg);
        when(gameService.nextRound(game.getId(), round)).thenReturn(gameResult);

        mockMvc.perform(put(urlTemplate).contentType(MediaType.APPLICATION_JSON)
                        .content(playersRoundJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Disabled
    @Test
    public void successfulPrevRound() throws Exception {
        String urlTemplate = path + "/" + game.getId() + "/prev";
        List<PlayerRoundDto> prevRoundBids = game.getLastBidsRound();
        String prevRoundBidsJson = objectMapper.writeValueAsString(prevRoundBids);
        ServiceResult<Game> gameResult = ServiceResult.success(game);

        when(gameService.prevRound(game.getId())).thenReturn(gameResult);

        mockMvc.perform(put(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().string(prevRoundBidsJson));
    }

    @Test
    public void idGamePrevRoundNotFound() throws Exception {
        String urlTemplate = path + "/" + game.getId() + "/prev";
        String expectedErrMsg = "La partida no existe";
        ServiceResult<Game> gameResult = ServiceResult.error(expectedErrMsg);
        when(gameService.prevRound(game.getId())).thenReturn(gameResult);

        mockMvc.perform(put(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulFinishedGame() throws Exception {
        String urlTemplate = path + "/" + game.getId() + "/finish";
        FinishedGameDto finishedGameDto = new FinishedGameDto(game.getId(), "Facu", "Migue");
        ServiceResult<Game> serviceResult = ServiceResult.success(game);
        when(gameService.finishGame(any(FinishedGameDto.class))).thenReturn(serviceResult);

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(finishedGameDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(game)));
    }

    private <T> String toJson(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}