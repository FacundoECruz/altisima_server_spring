package com.facu.altisima.controller.dto;

import com.facu.altisima.model.Score;

import java.util.List;
import java.util.Objects;

public class PlayerResultDto {
    public static final int BASE_WIN_SCORE = 5;
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
        Integer lastRoundScore = history.get(history.size() - 1);
        history.remove(history.size() - 1);
        return new PlayerResultDto(
                username,
                score - lastRoundScore,
                history);
    }

    public void updateScore(Integer roundBid) {
        if (roundBid < 0) {
            score = score + roundBid;
            history.add(roundBid);
        } else {
            score = score + BASE_WIN_SCORE + roundBid;
            history.add(BASE_WIN_SCORE + roundBid);
        }
    }

    public Score toScore(){
        return new Score(username, score);
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
