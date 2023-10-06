package com.facu.altisima.repository.dto;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.controller.dto.legacyDtos.PlayerRoundWithHistory;
import com.facu.altisima.model.Game;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document(value = "games")
public class GameDto {
    String _id;
    List<Integer> cardsPerRound;
    List<List<PlayerRoundWithHistory>> results;
    String date;
    Integer round;
    List<String> players;
    Integer totalRounds = 9;

    public GameDto() {

    }

    public GameDto(String _id, List<Integer> cardsPerRound, List<List<PlayerRoundWithHistory>> results, String date, Integer round, List<String> players) {
        this._id = _id;
        this.cardsPerRound = cardsPerRound;
        this.results = results;
        this.date = date;
        this.round = round;
        this.players = players;
    }

    public static GameDto from(Game game) {
        List<List<PlayerRoundWithHistory>> results = generateResults(game.getLastBidsRound(), game.getCurrentResults(), game.getPlayersImgs());
        return new GameDto(
                game.get_id(),
                game.getCardsPerRound(),
                results,
                game.getDate(),
                game.getCurrentRound(),
                game.getPlayers());
    }

    private static List<List<PlayerRoundWithHistory>> generateResults(
            List<PlayerRoundDto> roundBids,
            List<PlayerResultDto> currentResults,
            List<String> playersImgs) {
        return getResultsContainer(roundBids, currentResults, playersImgs);
    }

    @NotNull
    private static List<List<PlayerRoundWithHistory>> getResultsContainer(
            List<PlayerRoundDto> roundBids,
            List<PlayerResultDto> currentResults,
            List<String> playersImgs) {
        List<List<PlayerRoundWithHistory>> resultsContainer = new ArrayList<>();
        List<PlayerRoundWithHistory> roundResults = getRoundResults(roundBids, currentResults, playersImgs);
        resultsContainer.add(roundResults);
        return resultsContainer;
    }

    @NotNull
    private static List<PlayerRoundWithHistory> getRoundResults(List<PlayerRoundDto> roundBids, List<PlayerResultDto> currentResults, List<String> playersImgs) {
        List<PlayerRoundWithHistory> roundResults = new ArrayList<>();
        for (int i = 0; i < roundBids.size(); i++) {
            PlayerRoundWithHistory playerRound = new PlayerRoundWithHistory(
                    currentResults.get(i).getUsername(),
                    currentResults.get(i).getScore(),
                    roundBids.get(i).getBid(),
                    roundBids.get(i).getBidsLost(),
                    playersImgs.get(i),
                    currentResults.get(i).getHistory());

            roundResults.add(playerRound);
        }
        return roundResults;
    }

    public Game toDomain() {
        List<PlayerRoundDto> lastBidsRound = generateLastBidsRound(results.get(results.size() - 1));
        List<PlayerResultDto> currentResults = generateCurrentResults(results.get(results.size() - 1));
        List<String> playersImgs = extractImages(results.get(results.size() - 1));
        return new Game(_id, date, round, cardsPerRound, players, currentResults, lastBidsRound, totalRounds, playersImgs);
    }

    private List<PlayerRoundDto> generateLastBidsRound(List<PlayerRoundWithHistory> roundResults) {
        return roundResults.stream().map(playerRound -> {
            return toRoundDto(playerRound);
        }).collect(Collectors.toList());
    }

    private PlayerRoundDto toRoundDto(PlayerRoundWithHistory playerRound) {
        return new PlayerRoundDto(
                playerRound.getUsername(),
                playerRound.getBid(),
                playerRound.getBidsLost());
    }

    private List<PlayerResultDto> generateCurrentResults(List<PlayerRoundWithHistory> roundResults) {
        return roundResults.stream().map(playerResult -> {
            return new PlayerResultDto(
                    playerResult.getUsername(),
                    playerResult.getScore(),
                    playerResult.getHistory());
        }).collect(Collectors.toList());
    }

    private List<String> extractImages(List<PlayerRoundWithHistory> results) {
        return results.stream().map(PlayerRoundWithHistory::getImage).collect(Collectors.toList());
    }

    public List<List<PlayerRoundWithHistory>> getResults() {
        return results;
    }
}


