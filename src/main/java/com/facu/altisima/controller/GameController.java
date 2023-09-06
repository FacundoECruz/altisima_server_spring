package com.facu.altisima.controller;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.GameServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games/api")
public class GameController {
    @Autowired
    private GameServiceAPI gameServiceAPI;

    @GetMapping(value = "/{id}")
    public Game getGameById(@PathVariable String id) {
        return gameServiceAPI.get(id);
    }

    @PostMapping
    public ResponseEntity<Game> saveGame(@RequestBody Game game) {
        Game obj = gameServiceAPI.save(game);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameServiceAPI.getAll();
    }

    @DeleteMapping(value="/{id}")
    public String deleteGame(@PathVariable String id) {
        Game game = gameServiceAPI.get(id);
        if (game != null) {
            gameServiceAPI.delete(id);
        } else {
            return "Game does not exists";
        }

        return "Successfully deleted";
    }
}

