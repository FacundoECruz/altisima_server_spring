package com.facu.altisima.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Player {
    private String username;
    private String image;
    private Integer gamesWon;
    private Integer gamesPlayed;
    private Integer totalScore;

    public Player() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(username, player.username) && Objects.equals(image, player.image) && Objects.equals(gamesWon, player.gamesWon) && Objects.equals(gamesPlayed, player.gamesPlayed) && Objects.equals(totalScore, player.totalScore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, image, gamesWon, gamesPlayed, totalScore);
    }

    public Player(String username, String image, Integer gamesWon, Integer gamesPlayed, Integer totalScore) {
        this.username = username;
        this.image = image;
        this.gamesWon = gamesWon;
        this.gamesPlayed = gamesPlayed;
        this.totalScore = totalScore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(Integer gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }


}
