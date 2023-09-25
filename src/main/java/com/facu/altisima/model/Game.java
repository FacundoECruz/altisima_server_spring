package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
@Document
public class Game {
    @Id
    private ObjectId _id;
    private String date;
    public Integer currentRound;
    public List<Integer> cardsPerRound;
    private List<String> players;
    private List<PlayerResultDto> currentResults;
    private List<PlayerRoundDto> lastBidsRound;
    private Integer totalRounds;
    private List<String> playersImgs;

    public Game() {

    }

    public Game(ObjectId _id, String date, Integer currentRound, List<Integer> cardsPerRound, List<String> players, List<PlayerResultDto> currentResults, List<PlayerRoundDto> lastBidsRound, Integer totalRounds, List<String> playersImgs) {
        this._id = _id;
        this.date = date;
        this.currentRound = currentRound;
        this.cardsPerRound = cardsPerRound;
        this.players = players;
        this.currentResults = currentResults;
        this.lastBidsRound = lastBidsRound;
        this.totalRounds = totalRounds;
        this.playersImgs = playersImgs;
    }

    public List<String> getPlayersImgs() {
        return playersImgs;
    }

    public void setPlayersImgs(List<String> playersImgs) {
        this.playersImgs = playersImgs;
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = _id;
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

    public List<Integer> getCardsPerRound() {
        return cardsPerRound;
    }

    public void setCardsPerRound(List<Integer> cardsPerRound) {
        this.cardsPerRound = cardsPerRound;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<PlayerResultDto> getRoundResults() {
        return currentResults;
    }

    public void setRoundResults(List<PlayerResultDto> roundResults) {
        this.currentResults = roundResults;
    }

    public Integer getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(Integer totalRounds) {
        this.totalRounds = totalRounds;
    }

    public List<PlayerResultDto> getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(List<PlayerResultDto> currentResults) {
        this.currentResults = currentResults;
    }

    public List<PlayerRoundDto> getLastBidsRound() {
        return lastBidsRound;
    }


    public void setLastBidsRound(List<PlayerRoundDto> lastBidsRound) {
        this.lastBidsRound = lastBidsRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(_id, game._id) && Objects.equals(date, game.date) && Objects.equals(currentRound, game.currentRound) && Objects.equals(cardsPerRound, game.cardsPerRound) && Objects.equals(players, game.players) && Objects.equals(currentResults, game.currentResults) && Objects.equals(lastBidsRound, game.lastBidsRound) && Objects.equals(totalRounds, game.totalRounds) && Objects.equals(playersImgs, game.playersImgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, date, currentRound, cardsPerRound, players, currentResults, lastBidsRound, totalRounds, playersImgs);
    }
}
