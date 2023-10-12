package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.model.User;

import java.util.List;

public class FinishGameRequestDto {
    List<PlayerRoundWithHistory> finishPlayersResult;
    String gameId;
    String host;
    String winner;

    public FinishGameRequestDto(
            List<PlayerRoundWithHistory> finishPlayersResult,
            String gameId,
            String host,
            String winner) {
        this.finishPlayersResult = finishPlayersResult;
        this.gameId = gameId;
        this.host = host;
        this.winner = winner;
    }

    public FinishGameRequestDto(){

    }

    public List<PlayerRoundWithHistory> getFinishPlayersResult() {
        return finishPlayersResult;
    }

    public void setFinishPlayersResult(
            List<PlayerRoundWithHistory> finishPlayersResult) {
        this.finishPlayersResult = finishPlayersResult;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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
