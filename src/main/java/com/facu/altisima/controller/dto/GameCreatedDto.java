package com.facu.altisima.controller.dto;

import com.facu.altisima.model.Game;

import java.util.List;
import java.util.Objects;

public class GameCreatedDto {
    String id;
    List<Integer> cardsPerRound;
    List<String> players;

    public GameCreatedDto(String id, List<Integer> cardsPerRound, List<String> players) {
        this.id = id;
        this.cardsPerRound = cardsPerRound;
        this.players = players;
    }

    public GameCreatedDto(Game game) {
        this.id = game.getId();
        this.cardsPerRound = game.getCardsPerRound();
        this.players = game.getPlayers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCreatedDto that = (GameCreatedDto) o;
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
