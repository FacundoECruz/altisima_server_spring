package com.facu.altisima.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class GamesController {

    @GetMapping("/games")
    public String allGames() {

        return "All games route";
    }

    @GetMapping("/games/game")
    public String game(@RequestParam(name = "id") String gameId) {

        return "Game id: " + gameId;
    }

    @PostMapping("/games")
    public String newGame() {

        return "New game created";
    }

    @PatchMapping("/games/next")
    public String patchNext() {
        return "Next round saved";
    }

    @PatchMapping("/games/prev")
    public String patchPrev() {
        return "Prev round";
    }

    @PatchMapping("/games/finish")
    public String finish() {
        return "Finish game";
    }
}

