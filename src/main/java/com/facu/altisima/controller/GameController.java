package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
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
    public List<Game> getAllGames() {
        return gameServiceAPI.getAllGames();
    }

    @PostMapping
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        Game obj = gameServiceAPI.saveGame(game);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public Game getGameById(@PathVariable String id) {
        return gameServiceAPI.getGame(id);
    }

    @PutMapping(value="/{id}/next")
    public GameState nextRound(@PathVariable String id) {
        Game game = gameServiceAPI.nextRound(id);
        GameState gameState = game.toGameState();
        return gameState;
    }
    @DeleteMapping(value="/{id}")
    public String deleteGame(@PathVariable String id) {
        Game game = gameServiceAPI.getGame(id);
        if (game != null) {
            gameServiceAPI.delete(id);
        } else {
            return "Game does not exists";
        }

        return "Successfully deleted";
    }
}

