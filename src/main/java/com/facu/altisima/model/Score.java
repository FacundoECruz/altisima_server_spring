package com.facu.altisima.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Score {
    @JsonProperty("username")
    private String username;
    @JsonProperty("score")
    private Integer score;

    public Score(String username, Integer score) {
        this.username = username;
        this.score = score;
    }
}
