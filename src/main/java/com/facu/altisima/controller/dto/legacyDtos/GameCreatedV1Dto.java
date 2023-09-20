package com.facu.altisima.controller.dto.legacyDtos;

import java.util.List;
import java.util.Objects;

public class GameCreatedV1Dto {
    private String id;
    private Integer round;
    private List<Integer> cardsPerRound;
    private String status;
    private List<PlayerWithImageV1> playersWithImages;

    public GameCreatedV1Dto(String id, Integer round, List<Integer> cardsPerRound, String status, List<PlayerWithImageV1> playersWithImages) {
        this.id = id;
        this.round = round;
        this.cardsPerRound = cardsPerRound;
        this.status = status;
        this.playersWithImages = playersWithImages;
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

    public List<PlayerWithImageV1> getPlayersWithImages() {
        return playersWithImages;
    }

    public void setPlayersWithImages(List<PlayerWithImageV1> playersWithImages) {
        this.playersWithImages = playersWithImages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameCreatedV1Dto that = (GameCreatedV1Dto) o;
        return Objects.equals(id, that.id) && Objects.equals(round, that.round) && Objects.equals(cardsPerRound, that.cardsPerRound) && Objects.equals(status, that.status) && Objects.equals(playersWithImages, that.playersWithImages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, round, cardsPerRound, status, playersWithImages);
    }
}
