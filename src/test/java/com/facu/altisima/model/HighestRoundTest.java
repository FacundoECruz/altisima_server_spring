package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HighestRoundTest {
    public static final int TOTAL_ROUNDS = 9;
    List<String> scoredTenOrMoreInARound = new ArrayList<>();
    List<Score> highestScoreInARound = new ArrayList<>();
    List<PlayerResultDto> gameResults = new ArrayList<>();
    HighestRound highestRound;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        scoredTenOrMoreInARound.add("Macri");
        scoredTenOrMoreInARound.add("Bulrich");
        Score highestInARound = new Score("Massa", 11);
        highestScoreInARound.add(highestInARound);
        PlayerResultDto playerResult = generateResults();
        gameResults.add(playerResult);
        highestRound = new HighestRound(scoredTenOrMoreInARound, highestScoreInARound, gameResults);
    }
    private PlayerResultDto generateResults() {
        List<Integer> history = new ArrayList<>();
        for(int i = 0; i < TOTAL_ROUNDS; i++){
            history.add(i + 2);
        }
        return new PlayerResultDto("Bregman", 50, history);
    }

    @Test
    public void should_put_into_the_list_player_with_10_points() {
        ServiceResult<List<String>> result = highestRound.checkTenOrMoreInARound();
        Assertions.assertTrue(result.getData().contains("Bregman"));
    }

    @Test
    public void should_put_into_the_list_once_for_each_with_more_than_10() {
        List<Integer> history = generateHistoryWithMoreThanTenPointsMoreThanOnce();
        gameResults.get(0).setHistory(history);
        ServiceResult<List<String>> result = highestRound.checkTenOrMoreInARound();
        Assertions.assertTrue(result.getData().contains("Bregman"));
    }

    private List<Integer> generateHistoryWithMoreThanTenPointsMoreThanOnce() {
        List<Integer> history = new ArrayList<>();
        for(int i = 0; i < TOTAL_ROUNDS; i++){
            history.add(i + 3);
        }
        return history;
    }
    @Test
    public void should_return_list_of_players_who_reached_highest_round_score(){
        setNewHistory();
        List<Score> expected = setExpectedListWithTwoPlayers();
        ServiceResult<List<Score>> result = highestRound.checkHighestRound();
        Assertions.assertEquals(expected, result.getData());
    }

    private void setNewHistory() {
        List<Integer> history = generateHistoryWithMoreThanTenPointsMoreThanOnce();
        gameResults.get(0).setHistory(history);
    }
    private List<Score> setExpectedListWithTwoPlayers() {
        List<Score> expected = new ArrayList<>();
        Score player = new Score("Bregman", 11);
        expected.add(highestScoreInARound.get(0));
        expected.add(player);
        return expected;
    }

    @Test
    public void should_update_the_list_with_the_new_record() {
        setRecordHistory();
        List<Score> expected = generateExpectedWithOnePlayer();
        ServiceResult<List<Score>> result = highestRound.checkHighestRound();
        Assertions.assertEquals(expected, result.getData());
    }

    private void setRecordHistory() {
        List<Integer> history = generateRecordHistory();
        gameResults.get(0).setHistory(history);
    }
    private List<Integer> generateRecordHistory() {
        List<Integer> history = new ArrayList<>();
        for(int i = 0; i < TOTAL_ROUNDS; i++){
            history.add(i + 4);
        }
        return history;
    }
    private List<Score> generateExpectedWithOnePlayer() {
        List<Score> expected = new ArrayList<>();
        Score player = new Score("Bregman", 12);
        expected.add(player);
        return expected;
    }
}
