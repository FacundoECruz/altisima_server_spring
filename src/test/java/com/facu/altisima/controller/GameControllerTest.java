package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.service.utils.ServiceResult;
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

        GameRequestDto gameRequestDto = new GameRequestDto(players, totalRounds);

        String gameRequestJson = objectMapper.writeValueAsString(gameRequestDto);
        GameCreatedDto gameCreatedDto = new GameCreatedDto(game);
        String gameCreatedJson = objectMapper.writeValueAsString(gameCreatedDto);

        mockMvc.perform(post("/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameCreatedJson));
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
        Game game = gameGenerator.generate(players, totalRounds);
        List<Game> games = new ArrayList<>();
        games.add(game);

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

        when(gameService.delete(game.getId())).thenReturn(ServiceResult.success(successfulDeleteMsg));

        String urlTemplate = "/games/" + game.getId();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().string(successfulDeleteMsg));
    }

    @Test
    public void gameToDeleteNotFound() throws Exception {
        String unsuccessfulDeleteMsg = "No se encontro la partida";
        Game game = gameGenerator.generate(players, totalRounds);
        ServiceResult<Game> gameServiceResult = ServiceResult.success(game);

        when(gameService.delete(game.getId())).thenReturn(ServiceResult.error(unsuccessfulDeleteMsg));

        String urlTemplate = "/games/" + game.getId();

        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(unsuccessfulDeleteMsg));
    }

    @Test
    public void successfulNextRound() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        List<PlayerRoundDto> round = gameGenerator.generateRoundBids(players);

        String urlTemplate = "/games/" + game.getId() + "/next";
        String playersRoundJson = objectMapper.writeValueAsString(round);

        ServiceResult<Game> gameServiceResult = ServiceResult.success(game);
        when(gameService.nextRound(game.getId(), round)).thenReturn(gameServiceResult);

        GameStateDto gameStateDto = new GameStateDto(gameServiceResult.getData());
        String gameStateJson = objectMapper.writeValueAsString(gameStateDto);

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playersRoundJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gameStateJson));
    }

    @Test
    public void unsuccessfulNextRound() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        List<PlayerRoundDto> round = gameGenerator.generateRoundBids(players);

        String urlTemplate = "/games/" + game.getId() + "/next";
        String playersRoundJson = objectMapper.writeValueAsString(round);
        String expectedErrMsg = "Algun mensaje de error";

        ServiceResult<Game> gameResult = ServiceResult.error(expectedErrMsg);
        when(gameService.nextRound(game.getId(), round)).thenReturn(gameResult);

        mockMvc.perform(put(urlTemplate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playersRoundJson))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulPrevRound() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        String urlTemplate = "/games/" + game.getId() + "/prev";
        List<PlayerRoundDto> prevRoundBids = game.getLastBidsRound();
        String prevRoundBidsJson = objectMapper.writeValueAsString(prevRoundBids);
        ServiceResult<List<PlayerRoundDto>> prevRoundBidsService= ServiceResult.success(prevRoundBids);

        when(gameService.prevRound(game.getId())).thenReturn(prevRoundBidsService);

        mockMvc.perform(put(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().string(prevRoundBidsJson));
    }

    @Test
    public void idGamePrevRoundNotFound() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        String urlTemplate = "/games/" + game.getId() + "/prev";
        String expectedErrMsg = "La partida no existe";
        ServiceResult<List<PlayerRoundDto>> prevRoundBidsError = ServiceResult.error(expectedErrMsg);
        when(gameService.prevRound(game.getId())).thenReturn(prevRoundBidsError);

        mockMvc.perform(put(urlTemplate))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedErrMsg));
    }

    @Test
    public void successfulFinishedGame() throws Exception {
        Game game = gameGenerator.generate(players, totalRounds);
        String urlTemplate = "/games/" + game.getId() + "/finish";
        String expectedMsg = "Se guardaron los datos de la partida";
        ServiceResult<String> serviceResult = ServiceResult.success(expectedMsg);
        FinishedGameDto finishedGameDto = new FinishedGameDto(game.getId(), "Facu", "Migue");
        when(gameService.finishGame(finishedGameDto)).thenReturn(serviceResult);

        mockMvc.perform(put(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMsg));
    }


}