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
@CrossOrigin(origins = "*")
public class PlayerController {
    @Autowired
    private PlayerServiceAPI playerServiceAPI;

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> find(@PathVariable String username) {
        ServiceResult<Player> player = playerServiceAPI.get(username);
        if(player.getErrorMessage() == null) {
            return new ResponseEntity<>(player.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(player.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public ResponseEntity<?> getAllPlayers() {
        ServiceResult<List<Player>> allPlayers = playerServiceAPI.getAll();
        if (allPlayers.getErrorMessage() == null) {
            return new ResponseEntity<>(allPlayers.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(allPlayers.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> savePlayer(@RequestBody Player player) {
        ServiceResult<Player> savedPlayer = playerServiceAPI.save(player);
        if(savedPlayer.getErrorMessage() == null) {
            return new ResponseEntity<>(savedPlayer.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedPlayer.getErrorMessage(), HttpStatus.CONFLICT);
        }
    }

}
