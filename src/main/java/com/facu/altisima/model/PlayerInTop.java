package com.facu.altisima.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInTop {
    @JsonProperty("username")
    private String username;
    @JsonProperty("gamesWon")
    private Integer gamesWon;
    @JsonProperty("totalScore")
    private Integer totalScore;

    public PlayerInTop(String username, Integer gamesWon, Integer totalScore) {
        this.username = username;
        this.gamesWon = gamesWon;
        this.totalScore = totalScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }
}
