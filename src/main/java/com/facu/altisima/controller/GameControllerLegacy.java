package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.GameStateDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.controller.dto.legacyDtos.*;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/games")
public class GameControllerLegacy {
    @Autowired
    private GameServiceAPI gameServiceAPI;

    @PostMapping(value = "/v1")
    public ResponseEntity<?> saveGameV1(@RequestBody List<PlayersRoundAndScoreDto> players){
        Integer totalRounds = 9;
        //Create List<String> to pass service
        List<String> playersNames = new ArrayList<>();
        for(int i = 0; i < players.size(); i++){
            playersNames.add(players.get(i).getUsername());
        }

        ServiceResult<Game> receivedGameFromService = gameServiceAPI.createGame(playersNames, totalRounds);
        Game game = receivedGameFromService.getData();

        //Generate List of players with images to return in GameCreatedV1
        List<PlayerWithImageV1> playersWithImagesList = new ArrayList<>();
        for(int i = 0; i < players.size(); i++) {
            PlayerWithImageV1 playerWithImageV1 = new PlayerWithImageV1(players.get(i).getUsername(), players.get(i).getScore(), players.get(i).getBid(), players.get(i).getBidsLost(), game.getPlayersImgs().get(i));
            playersWithImagesList.add(playerWithImageV1);
        }

        GameCreatedV1Dto gameCreatedV1 = new GameCreatedV1Dto(game.getId(), game.getCurrentRound(), game.getCardsPerRound(), "inProgress", playersWithImagesList);

        return new ResponseEntity<>(gameCreatedV1, HttpStatus.OK);
    }

    @PutMapping(value = "/next")
    public ResponseEntity<?> nextRound(@RequestBody NextRoundDto nextRoundDto) {
        String id = nextRoundDto.getGameId();
        List<PlayerRoundWithHistory> roundData = nextRoundDto.getPlayersRound();
        List<PlayerRoundDto> playersRound = new ArrayList<>();
        for(int i = 0; i < roundData.size(); i++) {
            PlayerRoundDto playerRoundDto = new PlayerRoundDto(roundData.get(i).getUsername(), roundData.get(i).getBid(), roundData.get(i).getBidsLost());
            playersRound.add(playerRoundDto);
        }

        //Service call
        ServiceResult<Game> receivedGameFromService = gameServiceAPI.nextRound(id, playersRound);
        Game game = receivedGameFromService.getData();

        List<PlayerRoundWithHistory> newRoundState = new ArrayList<>();
        //Armar la respuesta: utiliza username: player.username,
        //      score: player.score, (actualizado)
        //      history: player.history, (actualizada)
        //      bid: 0,
        //      bidsLost: 0,
        //      image: player.image,

        NextRoundResponseDto nextRoundResponse = new NextRoundResponseDto(game.getCurrentRound(), newRoundState, "inProgress");
    }
}
