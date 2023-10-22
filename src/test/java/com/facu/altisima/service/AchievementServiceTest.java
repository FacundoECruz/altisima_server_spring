package com.facu.altisima.service;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.model.*;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.UserRepository;
import com.facu.altisima.service.impl.AchievementService;
import com.facu.altisima.service.utils.FirstReport;
import com.facu.altisima.utils.GameGenerator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class AchievementServiceTest {
    public static final int INSIGNIFICANT_NUMBER = 5;

    public static final int TOTAL_ROUNDS = 8;
    AchievementService achievementService = new AchievementService();
    AchievementRepository achievementRepository;
    AchievementReport achievementReport;
    Game game;
    @BeforeEach
    public void setup() {
        generateFinishedGame();
        generateAchievmentReport();
        this.achievementRepository = mock(AchievementRepository.class);
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
            PlayerResultDto playerResult = new PlayerResultDto(playersList.get(i), (i + 5) * 7, genericHistory);
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
        PlayerInTop player2 = new PlayerInTop("Cristiano", 6, 492);
        PlayerInTop player3 = new PlayerInTop("Mbappe", 3, 214);
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
    public void should_return_the_updated_highest_score_in_a_game() {
        List<AchievementReport> returnedReport = new ArrayList<>();
        returnedReport.add(achievementReport);
        when(achievementRepository.findAll()).thenReturn(returnedReport);
        achievementService.update(game);
        AchievementReport report = achievementService.getReport();
        updateAchievementReport();
        Assertions.assertEquals(report.getTopScoreInAGame(), achievementReport.getTopScoreInAGame());
    }

    private void updateAchievementReport() {
        List<Score> newHighestContainer = new ArrayList<>();
        Score newHighest = new Score("Pablin", 56);
        newHighestContainer.add(newHighest);
        achievementReport.setTopScoreInAGame(newHighestContainer);
    }

    @Test
    public void should_return_the_first_report() {
        AchievementReport currentReport = FirstReport.generate();
        when(achievementRepository.save(any(AchievementReport.class))).thenReturn(currentReport);
        achievementService.save();
        verify(achievementRepository, times(1)).save(currentReport);
    }

    @Test
    public void should_update_the_top1(){
        achievementService.update(game);
        AchievementReport report = achievementService.getReport();
    }
    @Test
    public void should_return_the_achievements_of_a_given_player() {

    }

    @Test
    public void should_response_with_no_achievements_when_player_does_not_have_any() {

    }
}
