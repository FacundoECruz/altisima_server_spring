package com.facu.altisima.controller.dto;

import java.util.List;
import java.util.Objects;

public class PlayerResultDto {
    private String username;
    private Integer score;
    private List<Integer> history;

    public PlayerResultDto(String username, Integer score, List<Integer> history) {
        this.username = username;
        this.score = score;
        this.history = history;
    }

    public PlayerResultDto() {

    }

    public PlayerResultDto prevRoundState() {
        Integer lastRoundScore = this.getHistory().get(getHistory().size() - 1);
        this.getHistory().remove(this.getHistory().size() - 1);
        PlayerResultDto prevRound = new PlayerResultDto(this.getUsername(),
                this.getScore() - lastRoundScore,
                this.getHistory());
        return prevRound;
    }

    public void updateHistory(Integer roundScore) {
        this.history.add(roundScore);
    }

    public List<Integer> getHistory() {
        return history;
    }

    public void setHistory(List<Integer> history) {
        this.history = history;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerResultDto that = (PlayerResultDto) o;
        return Objects.equals(username, that.username) && Objects.equals(score, that.score) && Objects.equals(history, that.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score, history);
    }
}
