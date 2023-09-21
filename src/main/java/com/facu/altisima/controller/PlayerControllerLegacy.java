package com.facu.altisima.controller;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/players")
public class PlayerControllerLegacy {
    @Autowired
    private PlayerServiceAPI playerService;

    //CREATE PLAYER
    @PostMapping
    public ResponseEntity<?> savePlayerV1(@RequestBody String playerName) {
        String defaultPlayerImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwUmQOGfv3HMATaiRo8hDCdcu23Otwqg2pEg&usqp=CAU";
        Player player = new Player(playerName, defaultPlayerImageUrl, 0, 0, 0);
        ServiceResult<Player> servicePlayer = playerService.save(player);
        Player createdPlayer = servicePlayer.getData();

        return new ResponseEntity<>(createdPlayer, HttpStatus.OK);
    }


}
