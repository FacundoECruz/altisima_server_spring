package com.facu.altisima.controller.dto.legacyDtos;

import java.util.List;
import java.util.Objects;

public class NextRoundResponseDto {
    private Integer round;
    private List<PlayerRoundWithHistory> newRoundState;
    private String status;

    public NextRoundResponseDto(Integer round, List<PlayerRoundWithHistory> newRoundState, String status) {
        this.round = round;
        this.newRoundState = newRoundState;
        this.status = status;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public List<PlayerRoundWithHistory> getNewRoundState() {
        return newRoundState;
    }

    public void setNewRoundState(List<PlayerRoundWithHistory> newRoundState) {
        this.newRoundState = newRoundState;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NextRoundResponseDto that = (NextRoundResponseDto) o;
        return Objects.equals(round, that.round) && Objects.equals(newRoundState, that.newRoundState) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(round, newRoundState, status);
    }
}
