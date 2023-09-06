package com.facu.altisima.controller.dto;

import java.util.Objects;

public class PlayerRound {
    private String username;
    private Integer score;
    private Integer bid;
    private Integer bidsLost;

    public PlayerRound(String username, Integer score, Integer bid, Integer bidsLost) {
        this.username = username;
        this.score = score;
        this.bid = bid;
        this.bidsLost = bidsLost;
    }

    public PlayerRound() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRound that = (PlayerRound) o;
        return Objects.equals(username, that.username) && Objects.equals(score, that.score) && Objects.equals(bid, that.bid) && Objects.equals(bidsLost, that.bidsLost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score, bid, bidsLost);
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
}
