package com.facu.altisima.controller.dto;

import java.util.Objects;

public class PlayerRoundDto {
    private String username;
    private Integer bid;
    private Integer bidsLost;

    public PlayerRoundDto(String username, Integer bid, Integer bidsLost) {
        this.username = username;
        this.bid = bid;
        this.bidsLost = bidsLost;
    }

    public PlayerRoundDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerRoundDto that = (PlayerRoundDto) o;
        return Objects.equals(username, that.username) && Objects.equals(bid, that.bid) && Objects.equals(bidsLost, that.bidsLost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, bid, bidsLost);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
