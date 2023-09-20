package com.facu.altisima.controller.dto;

import java.util.Objects;

public class FinishedGameDto {
    String id;
    String host;
    String winner;

    public FinishedGameDto(String id, String host, String winner) {
        this.id = id;
        this.host = host;
        this.winner = winner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinishedGameDto that = (FinishedGameDto) o;
        return Objects.equals(id, that.id) && Objects.equals(host, that.host) && Objects.equals(winner, that.winner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, host, winner);
    }
}
