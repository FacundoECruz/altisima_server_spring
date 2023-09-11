package com.facu.altisima.controller;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerServiceAPI playerServiceAPI;

    @GetMapping
    public ResponseEntity<?> getAllPlayers() {
        ServiceResult<List<Player>> allPlayers = playerServiceAPI.getAll();
        if (allPlayers != null) {
            return new ResponseEntity<>(allPlayers.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(allPlayers.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Player> savePlayers(@RequestBody Player player) {
        Player obj = playerServiceAPI.save(player);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public Player find(@PathVariable String id) {
        return playerServiceAPI.get(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Player> putPlayer(@PathVariable String id, @RequestBody Player playerChanges) {
        Player player = playerServiceAPI.put(id, playerChanges);
        return new ResponseEntity<Player>(player, HttpStatus.OK);
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
}
