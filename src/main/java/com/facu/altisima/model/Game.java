package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

public class Game {
    @Id
    private String id;
    private String date;
    private Integer currentRound;

    private List<Integer> cardsPerRound;

    private List<Player> players;

    private List<PlayerRound> roundResults;


    public Game() {

    }

    public Game(String id, String date, Integer currentRound) {
        this.id = id;
        this.date = date;
        this.currentRound = currentRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(date, game.date) && Objects.equals(currentRound, game.currentRound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, currentRound);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }
}
