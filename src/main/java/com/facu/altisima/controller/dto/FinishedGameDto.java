package com.facu.altisima.controller.dto;

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
}
