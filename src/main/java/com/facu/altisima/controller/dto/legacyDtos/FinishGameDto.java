package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.model.User;

import java.util.List;

public class FinishGameDto {
    List<PlayerRoundWithHistory> finishPlayersResult;
    String gameId;
    User host;
    WinnerDto winner;

    public FinishGameDto(List<PlayerRoundWithHistory> finishPlayersResult, String gameId, User host, WinnerDto winner) {
        this.finishPlayersResult = finishPlayersResult;
        this.gameId = gameId;
        this.host = host;
        this.winner = winner;
    }

    public List<PlayerRoundWithHistory> getFinishPlayersResult() {
        return finishPlayersResult;
    }

    public void setFinishPlayersResult(List<PlayerRoundWithHistory> finishPlayersResult) {
        this.finishPlayersResult = finishPlayersResult;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public WinnerDto getWinner() {
        return winner;
    }

    public void setWinner(WinnerDto winner) {
        this.winner = winner;
    }
}
