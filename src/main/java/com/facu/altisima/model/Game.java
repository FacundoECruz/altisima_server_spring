package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;

import com.facu.altisima.controller.dto.legacyDtos.GameCreatedV1Dto;
import com.facu.altisima.controller.dto.legacyDtos.PlayerRoundAndScoreDto;
import com.facu.altisima.controller.dto.legacyDtos.PlayerRoundWithHistory;
import com.facu.altisima.controller.dto.legacyDtos.PlayerWithImageV1;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
import java.util.stream.Collectors;

public class Game {
    public static final int BASE_WIN_SCORE = 5;
    private String _id;
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

    public Game(String _id, String date, Integer currentRound, List<Integer> cardsPerRound, List<String> players, List<PlayerResultDto> currentResults, List<PlayerRoundDto> lastBidsRound, Integer totalRounds, List<String> playersImgs) {
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

    public List<PlayerRoundWithHistory> toNewRoundState() {
        List<PlayerRoundWithHistory> newRoundState = new ArrayList<>();
        List<PlayerResultDto> tableScore = currentResults;
        for (int i = 0; i < players.size(); i++) {
            String playerImage = playersImgs.get(i);
            PlayerResultDto playerResult = tableScore.get(i);
            PlayerRoundWithHistory playerRoundWithHistory = new PlayerRoundWithHistory(playerResult.getUsername(), playerResult.getScore(), 0, 0, playerImage, playerResult.getHistory());
            newRoundState.add(playerRoundWithHistory);
        }
        return newRoundState;
    }

    public GameCreatedV1Dto toGameCreatedV1Dto(List<PlayerRoundAndScoreDto> players){
        List<PlayerWithImageV1> playersWithImages = getPlayerWithImageV1s(players);
        return new GameCreatedV1Dto(
                _id,
                currentRound,
                cardsPerRound,
                "inProgress",
                playersWithImages);
    }

    private List<PlayerWithImageV1> getPlayerWithImageV1s(List<PlayerRoundAndScoreDto> players) {
        List<PlayerWithImageV1> playersWithImagesList = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            PlayerWithImageV1 playerWithImageV1 = new PlayerWithImageV1(
                    players.get(i).getUsername(),
                    players.get(i).getScore(),
                    players.get(i).getBid(),
                    players.get(i).getBidsLost(),
                    playersImgs.get(i));
            playersWithImagesList.add(playerWithImageV1);
        }
        return playersWithImagesList;
    }

    public Game turnBackOneRound() {
        Integer prevRound = currentRound - 1;
        List<PlayerResultDto> prevRoundResults = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            PlayerResultDto prevPlayerScore = currentResults.get(i).prevRoundState();
            prevRoundResults.add(prevPlayerScore);
        }
        return new Game(
                _id,
                date,
                prevRound,
                cardsPerRound,
                players,
                prevRoundResults,
                lastBidsRound,
                totalRounds,
                playersImgs);
    }

    public Game nextRound(List<PlayerRoundDto> roundBids){
        lastBidsRound = roundBids;
        currentRound += 1;
        for(int i = 0; i < roundBids.size(); i++){
            if(roundBids.get(i).getBidsLost() == 0)
                currentResults.get(i).updateScore(roundBids.get(i).getBid());
            else
                currentResults.get(i).updateScore(-roundBids.get(i).getBidsLost());
        }
        return this;
    }

    public List<String> getPlayersImgs() {
        return playersImgs;
    }

    public void setPlayersImgs(List<String> playersImgs) {
        this.playersImgs = playersImgs;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(date, game.date) && Objects.equals(currentRound, game.currentRound) && Objects.equals(cardsPerRound, game.cardsPerRound) && Objects.equals(players, game.players) && Objects.equals(currentResults, game.currentResults) && Objects.equals(lastBidsRound, game.lastBidsRound) && Objects.equals(totalRounds, game.totalRounds) && Objects.equals(playersImgs, game.playersImgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, currentRound, cardsPerRound, players, currentResults, lastBidsRound, totalRounds, playersImgs);
    }
}
