package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.model.Game;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;

public class GameCreatedV1Dto {
    private String id;
    private Integer round;
    private List<Integer> cardsPerRound;
    private String status;
    private List<PlayerWithImageV1> players;
    public GameCreatedV1Dto(String id, Integer round, List<Integer> cardsPerRound, String status, List<PlayerWithImageV1> players) {
        this.id = id;
        this.round = round;
        this.cardsPerRound = cardsPerRound;
        this.status = status;
        this.players = players;
    }

    public GameCreatedV1Dto(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public List<Integer> getCardsPerRound() {
        return cardsPerRound;
    }

    public void setCardsPerRound(List<Integer> cardsPerRound) {
        this.cardsPerRound = cardsPerRound;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PlayerWithImageV1> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerWithImageV1> players) {
        this.players = players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCreatedV1Dto that = (GameCreatedV1Dto) o;
        return Objects.equals(id, that.id)
                && Objects.equals(round, that.round)
                && Objects.equals(cardsPerRound, that.cardsPerRound)
                && Objects.equals(status, that.status)
                && Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                round,
                cardsPerRound,
                status,
                players);
    }
}
