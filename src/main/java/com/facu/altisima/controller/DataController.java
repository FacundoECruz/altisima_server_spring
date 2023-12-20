package com.facu.altisima.controller;

import com.facu.altisima.model.PlayerData;
import com.facu.altisima.service.api.DataServiceAPI;
import com.facu.altisima.service.impl.DataService;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
@CrossOrigin(origins = "*")
public class DataController {
    @Autowired
    private DataServiceAPI dataService;
    @GetMapping
    public ResponseEntity<?> getPlayersData() {
        ServiceResult<List<PlayerData>> data = dataService.getAll();
        if (data.isSuccess())
            return new ResponseEntity<>(data.getData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(data.getErrorMessage(), HttpStatus.CONFLICT);

    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> getPlayerData(@PathVariable String username) {
        ServiceResult<PlayerData> data = dataService.get(username);
        if (data.isSuccess())
            return new ResponseEntity<>(data.getData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(data.getErrorMessage(), HttpStatus.CONFLICT);

    }

    @PostMapping
    public ResponseEntity<?> updatePlayersData() {
        ServiceResult<String> response = dataService.update();
        if(response.isSuccess())
            return new ResponseEntity<>(response.getData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(response.getErrorMessage(), HttpStatus.CONFLICT);
    }
}
