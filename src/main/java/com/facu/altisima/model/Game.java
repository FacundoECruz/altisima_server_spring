package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResult;
import com.facu.altisima.controller.dto.PlayerRound;

import org.springframework.data.annotation.Id;

import java.util.*;

public class Game {
    @Id
    private String id;
    private String date;
    public Integer currentRound;
    public List<Integer> cardsPerRound;
    private List<String> players;
    private List<PlayerResult> currentResults;
    private List<PlayerRound> lastBidsRound;
    private Integer totalRounds;

    public Game() {

    }

    public Game(String id, String date, Integer currentRound, List<Integer> cardsPerRound, List<String> players, List<PlayerResult> currentResults, List<PlayerRound> lastBidsRound, Integer totalRounds) {
        this.id = id;
        this.date = date;
        this.currentRound = currentRound;
        this.cardsPerRound = cardsPerRound;
        this.players = players;
        this.currentResults = currentResults;
        this.lastBidsRound = lastBidsRound;
        this.totalRounds = totalRounds;
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

    public List<PlayerResult> getRoundResults() {
        return currentResults;
    }

    public void setRoundResults(List<PlayerResult> roundResults) {
        this.currentResults = roundResults;
    }

    public Integer getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(Integer totalRounds) {
        this.totalRounds = totalRounds;
    }

    public List<PlayerResult> getCurrentResults() {
        return currentResults;
    }

    public void setCurrentResults(List<PlayerResult> currentResults) {
        this.currentResults = currentResults;
    }

    public List<PlayerRound> getLastBidsRound() {
        return lastBidsRound;
    }


    public void setLastBidsRound(List<PlayerRound> lastBidsRound) {
        this.lastBidsRound = lastBidsRound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(id, game.id) && Objects.equals(date, game.date) && Objects.equals(currentRound, game.currentRound) && Objects.equals(cardsPerRound, game.cardsPerRound) && Objects.equals(players, game.players) && Objects.equals(currentResults, game.currentResults) && Objects.equals(lastBidsRound, game.lastBidsRound) && Objects.equals(totalRounds, game.totalRounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, currentRound, cardsPerRound, players, currentResults,lastBidsRound, totalRounds);
    }
}
