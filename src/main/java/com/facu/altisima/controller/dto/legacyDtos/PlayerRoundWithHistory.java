package com.facu.altisima.controller.dto.legacyDtos;

import java.util.List;
import java.util.Objects;

public class PlayerRoundWithHistory {
    private String username;
    private Integer score;
    private Integer bid;
    private Integer bidsLost;
    private String image;
    private List<Integer> history;

    public PlayerRoundWithHistory(String username, Integer score, Integer bid, Integer bidsLost, String image, List<Integer> history) {
        this.username = username;
        this.score = score;
        this.bid = bid;
        this.bidsLost = bidsLost;
        this.image = image;
        this.history = history;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public Integer getBidsLost() {
        return bidsLost;
    }

    public void setBidsLost(Integer bidsLost) {
        this.bidsLost = bidsLost;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRoundWithHistory that = (PlayerRoundWithHistory) o;
        return Objects.equals(username, that.username) && Objects.equals(score, that.score) && Objects.equals(bid, that.bid) && Objects.equals(bidsLost, that.bidsLost) && Objects.equals(image, that.image) && Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score, bid, bidsLost, image, history);
    }
}
