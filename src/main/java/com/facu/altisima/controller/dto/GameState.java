package com.facu.altisima.controller.dto;

import java.util.List;
import java.util.Objects;

public class GameState {
    private List<RoundStatus> round;
    private String status;
    private List<PlayerResult> results;

    public GameState(List<RoundStatus> round, String status, List<PlayerResult> results) {
        this.round = round;
        this.status = status;
        this.results = results;
    }

    public List<RoundStatus> getRound() {
        return round;
    }

    public void setRound(List<RoundStatus> round) {
        this.round = round;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlayerResult> getResults() {
        return results;
    }

    public void setResults(List<PlayerResult> results) {
        this.results = results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameState gameState = (GameState) o;
        return Objects.equals(round, gameState.round) && Objects.equals(status, gameState.status) && Objects.equals(results, gameState.results);
    }

    @Override
    public int hashCode() {
        return Objects.hash(round, status, results);
    }
}
