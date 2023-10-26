package com.facu.altisima.controller;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.AchievementServiceAPI;
import com.facu.altisima.service.impl.AchievementService;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/achievements")
@CrossOrigin(origins = "*")
public class AchievementController {
    @Autowired
    private AchievementServiceAPI achievementService;

    @GetMapping
    public ResponseEntity<?> getReport() {
        try {
            ServiceResult<AchievementReport> report = achievementService.getReport();
            if(report.isSuccess()){
                return new Response().build(report);
            } else {
                return new ResponseEntity<>(report.getErrorMessage(), HttpStatus.CONFLICT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveFirstReport(){
        try {
            ServiceResult<AchievementReport> report = achievementService.save();
            if(report.isSuccess()){
                return new Response().build(report);
            } else {
                return new ResponseEntity<>(report.getErrorMessage(), HttpStatus.CONFLICT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PostMapping(value = "update")
    public ResponseEntity<?> updateReport(@RequestBody Game game){
        try {
            ServiceResult<AchievementReport> report = achievementService.update(game);
            if(report.isSuccess()){
                return new Response().build(report);
            } else {
                return new ResponseEntity<>(report.getErrorMessage(), HttpStatus.CONFLICT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
