package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.controller.dto.legacyDtos.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.impl.GameServiceImpl;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.FixedIdGenerator;
import com.facu.altisima.utils.GameGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerLegacyTest {
    @MockBean
    GameServiceImpl gameService;
    @Autowired
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    GameGenerator gameGenerator = new GameGenerator();
    private List<PlayerRoundAndScoreDto> players;
    private List<PlayerRoundWithHistory> playersWithHistory;
    PlayerRoundWithHistory playerWithHistory;
    List<String> playersNames = new ArrayList<>();
    PlayerRoundAndScoreDto player;
    Integer totalRounds = 9;
    Game game;
    ServiceResult<Game> succeedGame;
    String path = "/v1/games";
    GameIdDto gameIdDto;
    String gameIdDtoJson;

    FixedIdGenerator idGenerator = new FixedIdGenerator("someRandomId");

    @BeforeEach
    public void setup() throws JsonProcessingException {
        players = new ArrayList<>();
        playersWithHistory = new ArrayList<>();
        player = new PlayerRoundAndScoreDto("Migue",0,0,0);
        playerWithHistory = new PlayerRoundWithHistory("Migue",0,0,0, "www.image.com/migue", new ArrayList<>());
        playersWithHistory.add(playerWithHistory);
        players.add(player);
        playersNames.add(player.getUsername());
        game = gameGenerator.generate(playersNames, totalRounds);
        succeedGame = ServiceResult.success(game);
        gameIdDto = new GameIdDto(game.get_id());
        gameIdDtoJson = objectMapper.writeValueAsString(gameIdDto);
    }

    @Test
    public void saveGameLegacy() throws Exception {
        when(gameService.createGame(playersNames, totalRounds)).thenReturn(succeedGame);
        GameCreatedV1Dto gameCreatedV1Dto = game.toGameCreatedV1Dto(players);
        mockMvc.perform(post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(players)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(gameCreatedV1Dto)));
    }

    @Test
    public void nextRoundLegacy() throws Exception {
        NextRoundDto nextRoundDto = new NextRoundDto(playersWithHistory, idGenerator.generate());
        when(gameService.nextRound(nextRoundDto.getGameId(), playersRound(nextRoundDto)))
                .thenReturn(succeedGame);
        RoundResponseDto nextRoundResponseDto = new RoundResponseDto(
                game.getCurrentRound(),
                game.toNewRoundState(),
                "in progress");

        mockMvc.perform(put(path + "/next")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(nextRoundDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(nextRoundResponseDto)));
    }

    private List<PlayerRoundDto> playersRound(NextRoundDto nextRoundDto) {
        return nextRoundDto.getPlayersRound().stream().map(PlayerRoundWithHistory::toDto).collect(Collectors.toList());
    }

    @Test
    public void prevRoundLegacy() throws Exception {
        GameIdDto idDto = new GameIdDto(game.get_id());
        when(gameService.prevRound(idDto.getId())).thenReturn(succeedGame);
        RoundResponseDto prevRoundResponse = new RoundResponseDto();

        mockMvc.perform(put(path + "/prev")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(idDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(prevRoundResponse.generate(game))));
    }

    @Test
    public void finishGameLegacy() throws Exception {
        FinishGameRequestDto finishGameDto = new FinishGameRequestDto(playersWithHistory, game.get_id(), "Migue", "Migue");
        FinishedGameDto finishedGameDto = new FinishedGameDto(finishGameDto.getGameId(), finishGameDto.getHost(), finishGameDto.getWinner());
        when(gameService.finishGame(finishedGameDto)).thenReturn(succeedGame);

        mockMvc.perform(put(path + "/finish")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(finishGameDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(toJson(succeedGame.getData())));
    }
    private <T> String toJson(T obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
