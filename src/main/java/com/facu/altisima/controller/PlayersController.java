package com.facu.altisima.controller;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/players/api")
public class PlayersController {

    @Autowired
    private PlayerServiceAPI playerServiceAPI;

    @GetMapping(value = "/")
    public List<Player> getAllPlayers() {
        return playerServiceAPI.getAll();
    }

    @GetMapping(value = "/{id}")
    public Player find(@PathVariable String id) {
        return playerServiceAPI.get(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Player> savePlayers(@RequestBody Player player) {
        Player obj = playerServiceAPI.save(player);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public String deletePlayers(@PathVariable String id) {
        Player player = playerServiceAPI.get(id);
        if (player != null) {
            playerServiceAPI.delete(id);
        } else {
            return "Player does not exists";
        }

        return "Successfully deleted";
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Player> putPlayer(@PathVariable String id, @RequestBody Player playerChanges) {
        Player player = playerServiceAPI.put(id, playerChanges);
        return new ResponseEntity<Player>(player, HttpStatus.OK);
    }
}
