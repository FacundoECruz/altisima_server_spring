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

@RestController
@RequestMapping("/v1/games")
@CrossOrigin(origins = "*")
public class GameControllerLegacy {
    @Autowired
    private GameServiceAPI gameServiceAPI;

    @PostMapping
    public ResponseEntity<?> saveGameV1(@RequestBody List<PlayerRoundAndScoreDto> players){
        //Translate for service
        Integer totalRounds = 9;
        List<String> playersNames = getPlayerNames(players);

        ServiceResult<Game> receivedGameFromService = gameServiceAPI.createGame(playersNames, totalRounds);
        Game game = receivedGameFromService.getData();

        //Translate for frontend
        List<PlayerWithImageV1> playersWithImages = getPlayerWithImageV1s(players, game);
        GameCreatedV1Dto gameCreatedV1 = new GameCreatedV1Dto(game.getId(), game.getCurrentRound(), game.getCardsPerRound(), "inProgress", playersWithImages);

        return new ResponseEntity<>(gameCreatedV1, HttpStatus.OK);
    }

    @NotNull
    private static List<String> getPlayerNames(List<PlayerRoundAndScoreDto> players) {
        List<String> playersNames = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            playersNames.add(players.get(i).getUsername());
        }
        return playersNames;
    }

    @NotNull
    private static List<PlayerWithImageV1> getPlayerWithImageV1s(List<PlayerRoundAndScoreDto> players, Game game) {
        List<PlayerWithImageV1> playersWithImagesList = new ArrayList<>();
        for(int i = 0; i < players.size(); i++) {
            PlayerWithImageV1 playerWithImageV1 = new PlayerWithImageV1(players.get(i).getUsername(), players.get(i).getScore(), players.get(i).getBid(), players.get(i).getBidsLost(), game.getPlayersImgs().get(i));
            playersWithImagesList.add(playerWithImageV1);
        }
        return playersWithImagesList;
    }

    @PutMapping(value = "/next")
    public ResponseEntity<?> nextRound(@RequestBody NextRoundDto nextRoundDto) {
        //Translate for service
        String id = nextRoundDto.getGameId();
        List<PlayerRoundWithHistory> roundData = nextRoundDto.getPlayersRound();
        List<PlayerRoundDto> playersRound = getPlayerRoundDtos(roundData);

        ServiceResult<Game> receivedGameFromService = gameServiceAPI.nextRound(id, playersRound);
        Game game = receivedGameFromService.getData();

        //Translate for frontend
        List<PlayerRoundWithHistory> newRoundState = getPlayerRoundWithHistories(game);
        RoundResponseDto nextRoundResponse = new RoundResponseDto(game.getCurrentRound(), newRoundState, "in progress");

        return new ResponseEntity<>(nextRoundResponse, HttpStatus.OK);
    }
    @NotNull
    private static List<PlayerRoundDto> getPlayerRoundDtos(List<PlayerRoundWithHistory> roundData) {
        List<PlayerRoundDto> playersRound = new ArrayList<>();
        for(int i = 0; i < roundData.size(); i++) {
            PlayerRoundDto playerRoundDto = new PlayerRoundDto(roundData.get(i).getUsername(), roundData.get(i).getBid(), roundData.get(i).getBidsLost());
            playersRound.add(playerRoundDto);
        }
        return playersRound;
    }

    @NotNull
    private static List<PlayerRoundWithHistory> getPlayerRoundWithHistories(Game game) {
        List<PlayerRoundWithHistory> newRoundState = new ArrayList<>();
        for(int i = 0; i < game.getPlayers().size(); i++){
            List<PlayerResultDto> tableScore = game.getCurrentResults();
            String playerImage = game.getPlayersImgs().get(i);
            PlayerResultDto playerResult = tableScore.get(i);
            PlayerRoundWithHistory playerRoundWithHistory = new PlayerRoundWithHistory(playerResult.getUsername(), playerResult.getScore(), 0, 0, playerImage, playerResult.getHistory());
            newRoundState.add(playerRoundWithHistory);
        }
        return newRoundState;
    }

    @PutMapping(value = "/prev")
    public ResponseEntity<?> prevRound(@RequestBody String gameId) {
        ServiceResult<Game> gameServiceResult = gameServiceAPI.prevRound(gameId);
        Game game = gameServiceResult.getData();

        //Translate for frontend
        List<PlayerRoundWithHistory> newRoundState = getPlayerRoundWithHistories(game);
        RoundResponseDto prevRoundResponse = new RoundResponseDto(game.getCurrentRound(), newRoundState, "in progress");

        return new ResponseEntity<>(prevRoundResponse, HttpStatus.OK);
    }

    @PutMapping(value = "/finish")
    public ResponseEntity<?> finishGame(@RequestBody FinishGameDto finishGameDto) {
        FinishedGameDto finishedGameDto = new FinishedGameDto(finishGameDto.getGameId(), finishGameDto.getHost().getUsername(), finishGameDto.getWinner().getUsername());
        ServiceResult<Game> finishedGame = gameServiceAPI.finishGame(finishedGameDto);
        return new ResponseEntity<>(finishedGame, HttpStatus.OK);
    }
}
