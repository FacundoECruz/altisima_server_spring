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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AchievementServiceTest {
    public static final int INSIGNIFICANT_NUMBER = 5;
    public static final int TOTAL_ROUNDS = 9;
    ObjectMapper objectMapper = new ObjectMapper();
    AchievementRepository achievementRepository;
    GameRepository gameRepository;
    PlayerRepository playerRepository;
    AchievementReport achievementReport;

    // Esto es porque todavia no resolvimos que onda la base de datos
    // Entonces estamos usando el findAll() que nos devuelve una lista
    List<AchievementReport> mockedReport = new ArrayList<>();
    Game game;
    private AchievementService achievementService;
    @BeforeEach
    public void setup() {
        generateFinishedGame();
        generateAchievmentReport();
        this.achievementRepository = mock(AchievementRepository.class);
        this.gameRepository = mock(GameRepository.class);
        this.playerRepository = mock(PlayerRepository.class);
        this.achievementService = new AchievementService(achievementRepository, gameRepository, playerRepository);
        mockedReport.add(achievementReport);
    }

    private void generateFinishedGame() {
        List<String> players = makePlayersList();
        makeGame(players);
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

    private void makeGame(List<String> players) {
        GameGenerator gameGenerator = new GameGenerator();
        game = gameGenerator.generate(players, TOTAL_ROUNDS);
        List<PlayerResultDto> finalResults = new ArrayList<>();
        generateFinalResults(players, finalResults);
        game.setCurrentResults(finalResults);
    }

    private void generateFinalResults(List<String> playersList, List<PlayerResultDto> finalResults) {
        for (int i = 0; i < playersList.size(); i++) {
            List<Integer> genericHistory = new ArrayList<>();
            for (int j = 0; j < TOTAL_ROUNDS; j++) {
                genericHistory.add(INSIGNIFICANT_NUMBER);
            }
            PlayerResultDto playerResult = new PlayerResultDto(
                    playersList.get(i),
                    (i + 5) * 7,
                    genericHistory);
            finalResults.add(playerResult);
        }
    }

    private void generateAchievmentReport() {
        List<PlayerInTop> top3 = generateTop3();
        List<Score> highestScoreInAGame = makeHighestScoreInAGame();
        List<Score> wasHighest = makeWasHighestScoreInAGame();
        List<String> tenOrMore = makeScoredTenOrMoreInARound();
        List<Score> highestInARound = makeHighestScoreInARound();
        achievementReport = new AchievementReport(top3,
                highestScoreInAGame,
                wasHighest,
                tenOrMore,
                highestInARound);
    }

    private static List<PlayerInTop> generateTop3() {
        List<PlayerInTop> top3 = new ArrayList<>();
        PlayerInTop player1 = new PlayerInTop("Messi", 8, 654);
        PlayerInTop player2 = new PlayerInTop("Cristiano", 7, 620);
        PlayerInTop player3 = new PlayerInTop("Mbappe", 6, 600);
        top3.add(player1);
        top3.add(player2);
        top3.add(player3);
        return top3;
    }

    private static List<Score> makeHighestScoreInAGame() {
        List<Score> topScoreInAGame = new ArrayList<>();
        Score topInAGame = new Score("Messi", 50);
        topScoreInAGame.add(topInAGame);
        return topScoreInAGame;
    }

    private static List<Score> makeWasHighestScoreInAGame() {
        List<Score> wasTopScoreInAGame = new ArrayList<>();
        Score wasTopScore = new Score("Cristiano", 49);
        wasTopScoreInAGame.add(wasTopScore);
        return wasTopScoreInAGame;
    }

    private static List<String> makeScoredTenOrMoreInARound() {
        List<String> scoredTenOrMoreInARound = new ArrayList<>();
        scoredTenOrMoreInARound.add("Antone");
        return scoredTenOrMoreInARound;
    }
    private static List<Score> makeHighestScoreInARound() {
        List<Score> highestScoreInARound = new ArrayList<>();
        Score highestInARound = new Score("Chaky", 11);
        highestScoreInARound.add(highestInARound);
        return highestScoreInARound;
    }


    @Test
    public void should_save_the_first_report() {
        AchievementReport currentReport = FirstReport.generate();
        when(achievementRepository.save(any(AchievementReport.class))).thenReturn(currentReport);
        ServiceResult<AchievementReport> returnedReport = achievementService.save();
        verify(achievementRepository, times(1)).save(any(AchievementReport.class));
    }

    @Test
    public void should_return_the_updated_highest_score_and_was_highest() throws JsonProcessingException {
        when(achievementRepository.findAll()).thenReturn(mockedReport);
        List<Score> expectedCurrentHighest = makeExpectedCurrentHighest();
        List<Score> expectedWasHighest = makeExpectedWasHighest();
        ServiceResult<AchievementReport> result = achievementService.update(game);
        Assertions.assertEquals(expectedCurrentHighest, result.getData().getTopScoreInAGame());
        Assertions.assertEquals(expectedWasHighest, result.getData().getWasTopScoreInAGame());
    }

    private List<Score> makeExpectedWasHighest() {
        List<Score> expected = new ArrayList<>();
        expected.add(achievementReport.getWasTopScoreInAGame().get(0));
        expected.add(achievementReport.getTopScoreInAGame().get(0));
        return expected;
    }

    private List<Score> makeExpectedCurrentHighest() {
        List<Score> expected = new ArrayList<>();
        Score playerHighest = new Score("Pablin", 56);
        expected.add(playerHighest);
        return expected;
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
        for(int i = 0; i < 3; i++){
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
        for(int i = 0; i < TOTAL_ROUNDS; i++){
            history.add(i + 4);
        }
        return history;
    }

    @Test
    public void should_update_the_top3() {
       //el top3 se actualiza de una forma distinta: pidiendo a la base
       //de datos la data ya actualizada del player que acaba de jugar.
        // Hay que pensar como lo vamos a testear aca.
    }
}
