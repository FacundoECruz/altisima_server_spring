package com.facu.altisima.controller.dto;

import java.util.List;
import java.util.Objects;

public class GameRequestDto {
    private List<String> players;
    private Integer totalRounds;

    public GameRequestDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRequestDto that = (GameRequestDto) o;
        return Objects.equals(players, that.players) && Objects.equals(totalRounds, that.totalRounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players, totalRounds);
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public Integer getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(Integer totalRounds) {
        this.totalRounds = totalRounds;
    }
}
