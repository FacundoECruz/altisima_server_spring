package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class HighestScoreTest {

    List<Score> currentHigest = new ArrayList<>();
    HighestScore highestScore;
    ObjectMapper objectMapper = new ObjectMapper();
    String newHighestUsername = "Messi";

    @BeforeEach
    public void setup() {
        Score current = new Score("Altisimo", 55);
        currentHigest.add(current);
        highestScore = new HighestScore(currentHigest);
    }

    @Test
    public void should_update_highest_game_score_if_is_new_record() throws JsonProcessingException {
        Integer newHighestScore = 57;
        List<PlayerResultDto> newHighest = generateResultDto(newHighestUsername, newHighestScore);
        List<Score> expectedResult = getNewRecord(newHighestScore);
        ServiceResult<List<Score>> result = highestScore.check(newHighest);
        Assertions.assertEquals(expectedResult, result.getData());
    }
    @NotNull
    private List<Score> getNewRecord(Integer newHighestScore) {
        List<Score> expectedResult = new ArrayList<>();
        Score player = new Score(newHighestUsername, newHighestScore);
        expectedResult.add(player);
        return expectedResult;
    }

    private List<PlayerResultDto> generateResultDto(String name, Integer score) {
        List<PlayerResultDto> container = new ArrayList<>();
        List<Integer> history = new ArrayList<>();
        PlayerResultDto player = new PlayerResultDto(name, score, history);
        container.add(player);
        return container;
    }
    @Test
    public void should_add_to_list_if_score_is_equal_to_record() throws JsonProcessingException {
        Integer newHighestScore = 55;
        List<PlayerResultDto> newHighest = generateResultDto(newHighestUsername, newHighestScore);
        List<Score> expectedResult = getEqualRecord(newHighestScore);
        ServiceResult<List<Score>> result = highestScore.check(newHighest);
        Assertions.assertEquals(result.getData(), expectedResult);
    }
    @NotNull
    private List<Score> getEqualRecord(Integer newHighestScore) {
        Score player = new Score(newHighestUsername, newHighestScore);
        List<Score> expectedResult = new ArrayList<>();
        expectedResult.add(currentHigest.get(0));
        expectedResult.add(player);
        return expectedResult;
    }
    @Test
    public void should_return_no_new_record_msg() {
        Integer newHighestScore = 50;
        List<PlayerResultDto> newHighest = generateResultDto(newHighestUsername, newHighestScore);
        ServiceResult<List<Score>> result = highestScore.check(newHighest);
        Assertions.assertEquals(result.getErrorMessage(), "No hubo cambios en el record actual");
    }
}
