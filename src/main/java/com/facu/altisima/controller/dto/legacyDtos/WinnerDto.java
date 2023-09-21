package com.facu.altisima.controller.dto.legacyDtos;

import java.util.List;

public class WinnerDto {
    String username;
    String score;
    String image;
    List<Integer> history;

    public WinnerDto(String username, String score, String image, List<Integer> history) {
        this.username = username;
        this.score = score;
        this.image = image;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void setHistory(List<Integer> history) {
        this.history = history;
    }
}
