package com.facu.altisima.service;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.model.*;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.service.impl.AchievementService;
import com.facu.altisima.service.utils.FirstReport;
import com.facu.altisima.service.utils.ServiceResult;
import com.facu.altisima.utils.GameGenerator;
import com.facu.altisima.utils.ReportGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class AchievementServiceTest {
    public static final int INSIGNIFICANT_NUMBER = 5;
    public static final int TOTAL_ROUNDS = 9;
    ObjectMapper objectMapper = new ObjectMapper();
    AchievementRepository achievementRepository;
    GameRepository gameRepository;
    PlayerRepository playerRepository;
    AchievementReport achievementReport;
    Game game;
    GameGenerator gameGenerator = new GameGenerator();
    ReportGenerator reportGenerator = new ReportGenerator();
    List<AchievementReport> mockedReport = new ArrayList<>();
    private AchievementService achievementService;

    @BeforeEach
    public void setup() {
        game = generateFinishedGame();
        achievementReport = reportGenerator.generate();
        this.achievementRepository = mock(AchievementRepository.class);
        this.gameRepository = mock(GameRepository.class);
        this.playerRepository = mock(PlayerRepository.class);
        this.achievementService = new AchievementService(achievementRepository, gameRepository, playerRepository);
        mockedReport.add(achievementReport);
    }

    private Game generateFinishedGame() {
        List<String> players = makePlayersList();
        return gameGenerator.generateFinished(players);
    }

    @NotNull
    private static List<String> makePlayersList() {
        List<String> players = new ArrayList<>();
        players.add("Migue");
        players.add("Chaky");
        players.add("Fonzo");
        players.add("Pablin");
        return players;
    }

    @Test
    public void should_save_the_first_report() {
        AchievementReport currentReport = FirstReport.generate();
        when(achievementRepository.save(any(AchievementReport.class))).thenReturn(currentReport);
        ServiceResult<AchievementReport> returnedReport = achievementService.save();
        verify(achievementRepository, times(1)).save(any(AchievementReport.class));
    }

    @Test
    public void return_error_message_when_report_not_found() {
        String message = "No se encontro el reporte";
        ServiceResult<AchievementReport> expected = ServiceResult.error(message);
        when(achievementRepository.findAll()).thenReturn(Collections.emptyList());
        ServiceResult<AchievementReport> result = achievementService.getReport();
        Assertions.assertEquals(expected.getErrorMessage(), result.getErrorMessage());
    }

    @Test
    public void should_return_the_updated_highest_score_and_was_highest() throws JsonProcessingException {
        when(achievementRepository.findAll()).thenReturn(mockedReport);
        List<Score> expectedCurrentHighest = makeExpectedCurrentHighest(56);
        List<Score> expectedWasHighest = makeExpectedWasHighest();
        ServiceResult<AchievementReport> result = achievementService.update(game);
        Assertions.assertEquals(expectedCurrentHighest, result.getData().getTopScoreInAGame());
        Assertions.assertEquals(expectedWasHighest, result.getData().getWasTopScoreInAGame());
    }

    private List<Score> makeExpectedCurrentHighest(int score) {
        List<Score> expected = new ArrayList<>();
        Score playerHighest = new Score("Pablin", score);
        expected.add(playerHighest);
        return expected;
    }

    private List<Score> makeExpectedWasHighest() {
        List<Score> expected = new ArrayList<>();
        expected.add(achievementReport.getWasTopScoreInAGame().get(0));
        expected.add(achievementReport.getTopScoreInAGame().get(0));
        return expected;
    }

    @Test
    public void should_add_the_new_record_and_not_update_was_highest_when_equal() throws JsonProcessingException {
        when(achievementRepository.findAll()).thenReturn(mockedReport);
        List<Score> expectedHighest = makeCurrentHighest();
        List<Score> currentWasHighest = mockedReport.get(0).getWasTopScoreInAGame();

        ServiceResult<AchievementReport> result = achievementService.update(game);
        Assertions.assertEquals(expectedHighest, result.getData().getTopScoreInAGame());
        Assertions.assertEquals(currentWasHighest, result.getData().getWasTopScoreInAGame());
    }

    private List<Score> makeCurrentHighest() throws JsonProcessingException {
        game.getCurrentResults().get(3).setScore(50);
        Score newRecord = new Score(game.getCurrentResults().get(3).getUsername(), game.getCurrentResults().get(3).getScore());
        List<Score> currentRecord = new ArrayList<>(mockedReport.get(0).getTopScoreInAGame());
        currentRecord.add(newRecord);
        return currentRecord;
    }

    @Test
    public void should_update_the_highest_score_in_round_and_more_than_ten() throws JsonProcessingException {
        List<Integer> recordHistory = generateRecordHistory();
        game.getCurrentResults().get(0).setHistory(recordHistory);
        when(achievementRepository.findAll()).thenReturn(mockedReport);
        List<String> expectedTenOrMore = makeExpectedTenOrMore();
        List<Score> expectedHighestInRound = makeExpectedHighestInRound();
        ServiceResult<AchievementReport> result = achievementService.update(game);
        Assertions.assertEquals(expectedTenOrMore, result.getData().getScoredTenOrMoreInARound());
        Assertions.assertEquals(expectedHighestInRound, result.getData().getHighestScoreInARound());
    }

    private List<String> makeExpectedTenOrMore() {
        List<String> expected = new ArrayList<>();
        expected.add(achievementReport.getScoredTenOrMoreInARound().get(0));
        for (int i = 0; i < 3; i++) {
            expected.add(game.getCurrentResults().get(0).getUsername());
        }
        return expected;
    }

    private List<Score> makeExpectedHighestInRound() {
        List<Score> expected = new ArrayList<>();
        Score player = new Score(game.getCurrentResults().get(0).getUsername(), 12);
        expected.add(player);
        return expected;
    }

    private List<Integer> generateRecordHistory() {
        List<Integer> history = new ArrayList<>();
        for (int i = 0; i < TOTAL_ROUNDS; i++) {
            history.add(i + 4);
        }
        return history;
    }

    @Test
    public void return_error_message_when_repo_return_empty() {
        String message = "Hubo un error en el repositorio";
        ServiceResult<AchievementReport> expected = ServiceResult.error(message);
        when(achievementRepository.findAll()).thenReturn(Collections.emptyList());
        ServiceResult<AchievementReport> result = achievementService.update(game);
        Assertions.assertEquals(expected.getErrorMessage(), result.getErrorMessage());
    }
}
