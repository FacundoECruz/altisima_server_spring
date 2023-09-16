package com.facu.altisima.controller.dto;

import java.util.Objects;

public class PlayerResultDto {
    private String username;
    private Integer score;

    public PlayerResultDto(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    public PlayerResultDto() {

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
        return Objects.equals(username, that.username) && Objects.equals(score, that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score);
    }
}
