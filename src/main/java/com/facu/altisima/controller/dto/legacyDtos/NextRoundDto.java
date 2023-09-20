package com.facu.altisima.controller.dto.legacyDtos;

import java.util.List;
import java.util.Objects;

public class NextRoundDto {
    private List<PlayerRoundWithHistory> playersRound;
    private String gameId;

    public NextRoundDto(List<PlayerRoundWithHistory> playersRound, String gameId) {
        this.playersRound = playersRound;
        this.gameId = gameId;
    }

    public List<PlayerRoundWithHistory> getPlayersRound() {
        return playersRound;
    }

    public void setPlayersRound(List<PlayerRoundWithHistory> playersRound) {
        this.playersRound = playersRound;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NextRoundDto that = (NextRoundDto) o;
        return Objects.equals(playersRound, that.playersRound) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playersRound, gameId);
    }
}
