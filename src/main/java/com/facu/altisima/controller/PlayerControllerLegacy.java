package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.legacyDtos.CreatePlayerDto;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.service.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
@CrossOrigin(origins = "*")
public class PlayerControllerLegacy {
    @Autowired
    private PlayerServiceAPI playerService;
    IdGenerator idGenerator = new UUIDGenerator();

    @GetMapping
    public ResponseEntity<?> getUnregisteredPlayers(){
        ServiceResult<List<Player>> allPlayers = playerService.getUnregistered();
        if (allPlayers.getErrorMessage() == null) {
            return new ResponseEntity<>(allPlayers.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(allPlayers.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> savePlayerV1(@RequestBody CreatePlayerDto playerName) {
        String defaultPlayerImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwUmQOGfv3HMATaiRo8hDCdcu23Otwqg2pEg&usqp=CAU";
        Player player = new Player(idGenerator.generate(), playerName.getUsername(), defaultPlayerImageUrl, 0, 0, 0);
        ServiceResult<Player> servicePlayer = playerService.save(player);
        Player createdPlayer = servicePlayer.getData();

        return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
    }

}
