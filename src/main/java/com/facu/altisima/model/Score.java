package com.facu.altisima.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


public class Score {
    @JsonProperty("username")
    private String username;
    @JsonProperty("score")
    private Integer score;

    public Score(String username, Integer score) {
        this.username = username;
        this.score = score;
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
        Score score1 = (Score) o;
        return Objects.equals(username, score1.username) && Objects.equals(score, score1.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, score);
    }
}
