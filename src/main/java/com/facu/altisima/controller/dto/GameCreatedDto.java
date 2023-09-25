package com.facu.altisima.controller.dto;

import com.facu.altisima.model.Game;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class GameCreatedDto {
    ObjectId _id;
    List<Integer> cardsPerRound;
    List<String> players;

    public GameCreatedDto(ObjectId _id, List<Integer> cardsPerRound, List<String> players) {
        this._id = _id;
        this.cardsPerRound = cardsPerRound;
        this.players = players;
    }

    public GameCreatedDto(Game game) {
        this._id = game.getId();
        this.cardsPerRound = game.getCardsPerRound();
        this.players = game.getPlayers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCreatedDto that = (GameCreatedDto) o;
        return Objects.equals(_id, that._id) && Objects.equals(cardsPerRound, that.cardsPerRound) && Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, cardsPerRound, players);
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = _id;
    }
}
