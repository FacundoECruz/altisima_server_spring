package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.controller.dto.legacyDtos.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/games")
@CrossOrigin(origins = "*")
public class GameControllerLegacy {
    @Autowired
    private GameServiceAPI gameServiceAPI;

    @PostMapping
    public ResponseEntity<?> saveGameV1(@RequestBody List<PlayerRoundAndScoreDto> players) {
        Integer totalRounds = 9;
        List<String> playersNames = getPlayerNames(players);

        ServiceResult<Game> receivedGameFromService = gameServiceAPI.createGame(playersNames, totalRounds);
        Game game = receivedGameFromService.getData();

        GameCreatedV1Dto gameCreatedV1 = game.toGameCreatedV1Dto(players);
        return new ResponseEntity<>(gameCreatedV1, HttpStatus.OK);
    }

    @NotNull
    private static List<String> getPlayerNames(List<PlayerRoundAndScoreDto> players) {
        List<String> playersNames = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            playersNames.add(players.get(i).getUsername());
        }
        return playersNames;
    }

    @PutMapping(value = "/next")
    public ResponseEntity<?> nextRound(@RequestBody NextRoundDto nextRoundDto) {
        ServiceResult<Game> gameService = getGameServiceResult(nextRoundDto);
        if (gameService.isError()) {
            return new ResponseEntity<>(gameService.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        RoundResponseDto nextRoundResponse = new RoundResponseDto(
                gameService.getData().getCurrentRound(),
                gameService.getData().toNewRoundState(),
                "in progress");

        return new ResponseEntity<>(nextRoundResponse, HttpStatus.OK);
    }

    private ServiceResult<Game> getGameServiceResult(NextRoundDto nextRoundDto) {
        List<PlayerRoundDto> playersRound = getPlayerRoundDtos(nextRoundDto);
        ServiceResult<Game> gameService = gameServiceAPI
                .nextRound(nextRoundDto.getGameId(), playersRound);
        return gameService;
    }

    private static List<PlayerRoundDto> getPlayerRoundDtos(NextRoundDto nextRoundDto) {
        List<PlayerRoundWithHistory> roundResults = nextRoundDto.getPlayersRound();
        return roundResults.stream().map(PlayerRoundWithHistory::toDto).collect(Collectors.toList());
    }

    @PutMapping(value = "/prev")
    public ResponseEntity<?> prevRound(@RequestBody GameIdDto idDto) {
        ServiceResult<Game> gameServiceResult = gameServiceAPI.prevRound(idDto.getId());
        if (gameServiceResult.isError()) {
            return new ResponseEntity<>(gameServiceResult.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        Game game = gameServiceResult.getData();
        RoundResponseDto prevRoundResponse = new RoundResponseDto();
        return new ResponseEntity<>(prevRoundResponse.generate(game), HttpStatus.OK);
    }

    @PutMapping(value = "/finish")
    public ResponseEntity<?> finishGame(@RequestBody FinishGameRequestDto finishGameDto) {
        FinishedGameDto finishedGameDto = new FinishedGameDto(finishGameDto.getGameId(), finishGameDto.getHost(), finishGameDto.getWinner());
        ServiceResult<Game> finishedGame = gameServiceAPI.finishGame(finishedGameDto);
        return new ResponseEntity<>(finishedGame.getData(), HttpStatus.OK);
    }
}
