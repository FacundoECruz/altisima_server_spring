package com.facu.altisima.controller.dto;

import java.util.List;
import java.util.Objects;

public class GameCreated {
    String id;
    List<Integer> cardsPerRound;
    List<String> players;

    public GameCreated(String id, List<Integer> cardsPerRound, List<String> players) {
        this.id = id;
        this.cardsPerRound = cardsPerRound;
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCreated that = (GameCreated) o;
        return Objects.equals(id, that.id) && Objects.equals(cardsPerRound, that.cardsPerRound) && Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardsPerRound, players);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
