package com.facu.altisima.service;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.model.*;
import com.facu.altisima.service.impl.AchievementService;
import com.facu.altisima.utils.GameGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AchievementServiceTest {
    AchievementService achievementService = new AchievementService();
    GameGenerator gameGenerator = new GameGenerator();
    Integer totalRounds = 9;
    Game game;
    List<String> players = new ArrayList<>();
    List<PlayerResultDto> finalResults = new ArrayList<>();
    List<PlayerInTop> top3 = new ArrayList<>();
    List<Score> topScoreInAGame = new ArrayList<>();
    List<Score> wasTopScoreInAGame = new ArrayList<>();
    List<String> scoredTenOrMoreInARound = new ArrayList<>();
    List<Score> highestScoreInARound = new ArrayList<>();

    @BeforeEach
    public void setup() {
        players.add("Migue");
        players.add("Chaky");
        players.add("Fonzo");
        players.add("Pablin");

        game = gameGenerator.generate(players, totalRounds);
        generateFinalResults();
        game.setCurrentResults(finalResults);

        addPlayersToTops();
    }

    private void addPlayersToTops() {
        PlayerInTop player1 = new PlayerInTop("Messi", 8, 654);
        PlayerInTop player2 = new PlayerInTop("Cristiano", 6, 492);
        PlayerInTop player3 = new PlayerInTop("Mbappe", 3, 214);
        top3.add(player1);
        top3.add(player2);
        top3.add(player3);

        Score topInAGame = new Score("Messi", 50);
        topScoreInAGame.add(topInAGame);
        Score wasTopScore = new Score("Cristiano", 49);
        wasTopScoreInAGame.add(wasTopScore);
        scoredTenOrMoreInARound.add("Antone");
        Score highestInARound = new Score("Chaky", 11);
        highestScoreInARound.add(highestInARound);
    }

    private void generateFinalResults() {
        for (int i = 0; i < players.size(); i++) {
            List<Integer> genericHistory = new ArrayList<>();
            for (int j = 0; j < totalRounds; j++) {
                genericHistory.add(5);
            }
            PlayerResultDto playerResult = new PlayerResultDto(players.get(i), (i + 5) * 7, genericHistory);
            finalResults.add(playerResult);
        }
    }

    @Test
    public void should_return_the_updated_achievements() {
        AchievementReport expectedReport = new AchievementReport(top3, topScoreInAGame, wasTopScoreInAGame, scoredTenOrMoreInARound, highestScoreInARound);
        achievementService.update(game);
        AchievementReport report = achievementService.getReport();
        Assertions.assertEquals(report, expectedReport);
    }

    @Test
    public void should_return_the_achievements_of_a_given_player() {

    }

    @Test
    public void should_response_with_no_achievements_when_player_does_not_have_any() {

    }
}
