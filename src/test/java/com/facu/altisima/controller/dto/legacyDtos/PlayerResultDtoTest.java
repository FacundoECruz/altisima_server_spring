package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.controller.dto.PlayerResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerResultDtoTest {

    public static final String USERNAME = "Facu";
    public static final int SCORE = 20;
    public static final int PREV_ROUND_SCORE = 6;
    public static final int BASE_WIN_SCORE = 5;
    PlayerResultDto playerResultDto;

    @BeforeEach
    public void setup() {
        List<Integer> history = new ArrayList<>();
        history.add(PREV_ROUND_SCORE);
        playerResultDto = new PlayerResultDto(USERNAME, SCORE, history);
    }

    @Test
    public void should_return_prev_round_state() {
        PlayerResultDto expected = new PlayerResultDto(
                USERNAME,
                SCORE - PREV_ROUND_SCORE,
                new ArrayList<>());
        PlayerResultDto result = playerResultDto.prevRoundState();

        assertEquals(expected, result);
    }

    @Test
    public void should_return_updated_score() {
        Integer roundBid = 2;
        List<Integer> updatedHistory = generateUpdatedHistory(roundBid);
        playerResultDto.updateScore(roundBid);
        assertEquals(SCORE + roundBid + BASE_WIN_SCORE, playerResultDto.getScore());
        assertEquals(updatedHistory, playerResultDto.getHistory());
    }

    private List<Integer> generateUpdatedHistory(Integer roundBid) {
        List<Integer> updatedHistory = new ArrayList<>();
        updatedHistory.add(PREV_ROUND_SCORE);
        updatedHistory.add(roundBid + BASE_WIN_SCORE);
        return updatedHistory;
    }
}
