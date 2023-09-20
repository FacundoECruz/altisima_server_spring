package com.facu.altisima.controller.dto.legacyDtos;

import java.util.Objects;

public class PlayerWithImageV1 {
    private String username;
    private Integer score;
    private Integer bid;
    private Integer bidsLost;
    private String image;

    public PlayerWithImageV1(String username, Integer score, Integer bid, Integer bidsLost, String image) {
        this.username = username;
        this.score = score;
        this.bid = bid;
        this.bidsLost = bidsLost;
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerWithImageV1 that = (PlayerWithImageV1) o;
        return Objects.equals(username, that.username) && Objects.equals(score, that.score) && Objects.equals(bid, that.bid) && Objects.equals(bidsLost, that.bidsLost) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score, bid, bidsLost, image);
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
}
