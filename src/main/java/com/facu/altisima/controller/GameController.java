package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.GameRequestDto;
import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.controller.dto.Translate;
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

    //    @GetMapping(value = "/{id}")
//    public Game getGameById(@PathVariable String id) {
//        return gameServiceAPI.getGame(id);
//    }

//    @GetMapping
//    public List<Game> getAllGames() {
//        return gameServiceAPI.getAllGames();
//    }

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
}

//    @PutMapping(value="/{id}/next")
//    public GameState nextRound(@PathVariable String id, @RequestBody List<PlayerRound> roundResults) {
//        Game game = gameServiceAPI.nextRound(id, roundResults);
//        Translate translator = new Translate();
//
//
//        return translator.toGameState(game);
//    }
//
//    @PutMapping(value="/{id}/prev")
//    public List<PlayerRound> prevRound(@PathVariable String id) {
//        return gameServiceAPI.prevRound(id);
//    }
//
//    @PutMapping(value="/{id}/finish")
//    public String finishGame(@PathVariable String id) {
//        Game game = gameServiceAPI.finishGame(id);
//        if(game != null) {
//            return "Game " + game.getId() + " saved in DB";
//        } else {
//            return "Game " + game.getId() + " is null";
//        }
//    }
//}

