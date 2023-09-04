package com.facu.altisima.controller;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;

import java.util.List;

@RestController
@RequestMapping("/players/api")
public class PlayersController {

    @Autowired
    private PlayerServiceAPI playerServiceAPI;

    @GetMapping(value = "/")
    public List<Player> getAll() {
        return playerServiceAPI.getAll();
    }

    @GetMapping(value = "/{id}")
    public Player find(@PathVariable String id) {
        return playerServiceAPI.get(id);
    }

    @PostMapping(value = "/")
    public ResponseEntity<Player> save(@RequestBody Player player) {
        Player obj = playerServiceAPI.save(player);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Player> delete(@PathVariable String id) {
        Player player = playerServiceAPI.get(id);
        if (player != null) {
            playerServiceAPI.delete(id);
        } else {
            return new ResponseEntity<Player>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<Player>(player, HttpStatus.OK);
    }
}
