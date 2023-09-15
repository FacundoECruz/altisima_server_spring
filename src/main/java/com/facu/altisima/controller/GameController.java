package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.GameRequestDto;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    @Autowired
    private GameServiceAPI gameServiceAPI;

    @GetMapping
    public ResponseEntity<?> getAllGames() {
        ServiceResult<List<Game>> allGames = gameServiceAPI.getAllGames();
        if (allGames.getErrorMessage() == null) {
            return new ResponseEntity<>(allGames.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(allGames.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveGame(@RequestBody GameRequestDto gameRequestDto) {
        List<String> players = gameRequestDto.getPlayers();
        Integer totalRounds = gameRequestDto.getTotalRounds();
        ServiceResult<Game> game = gameServiceAPI.createGame(players, totalRounds);
        if (game.getErrorMessage() == null) {
            return new ResponseEntity<>(game.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(game.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getGameById(@PathVariable String id) {
        ServiceResult<Game> game = gameServiceAPI.getGame(id);
        if(game.getErrorMessage() == null){
            return new ResponseEntity<>(game.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(game.getErrorMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable String id) {
        ServiceResult<String> result = gameServiceAPI.delete(id);
        if(result.getErrorMessage() == null) {
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}/next")
    public ResponseEntity<?> nextRound(@PathVariable String id, @RequestBody List<PlayerRound> playersRound) {
        ServiceResult<GameState> game = gameServiceAPI.nextRound(id, playersRound);
        if(game.getErrorMessage() == null) {
            GameState gameState = game.getData();
            return new ResponseEntity<>(gameState, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(game.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }
}




