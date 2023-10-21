package com.facu.altisima.model;

public class PlayerInTop {
    private String username;
    private Integer gamesWon;
    private Integer totalScore;

    public PlayerInTop(String username, Integer gamesWon, Integer totalScore) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.totalScore = totalScore;
    }
}
