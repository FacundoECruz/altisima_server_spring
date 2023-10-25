package com.facu.altisima.model;

import com.facu.altisima.service.AchievementServiceTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AchievementReportTest {

    List<Score> topScoreInAGame = new ArrayList<>();
    List<Score> wasTopScoreInAGame = new ArrayList<>();
    Score currentHighestScore = new Score("Messi", 55);
    Score currentWasHighestScore = new Score("Modric", 50);
    AchievementReport achievementReport;

    ObjectMapper objectMapper = new ObjectMapper();

    private AchievementReport generateAchievmentReport() {
        List<PlayerInTop> top3 = generateTop3();
        List<Score> highestScoreInAGame = makeHighestScoreInAGame();
        List<Score> wasHighest = makeWasHighestScoreInAGame();
        List<String> tenOrMore = makeScoredTenOrMoreInARound();
        List<Score> highestInARound = makeHighestScoreInARound();
        return new AchievementReport(top3,
                highestScoreInAGame,
                wasHighest,
                tenOrMore,
                highestInARound);
    }

    private static List<PlayerInTop> generateTop3() {
        List<PlayerInTop> top3 = new ArrayList<>();
        PlayerInTop player1 = new PlayerInTop("Messi", 8, 654);
        PlayerInTop player2 = new PlayerInTop("Cristiano", 7, 620);
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

    @BeforeEach
    public void setup(){
        topScoreInAGame.add(currentHighestScore);
        wasTopScoreInAGame.add(currentWasHighestScore);
        achievementReport = generateAchievmentReport();
    }

    @Test
    public void should_update_highest_score_in_a_game() {
        List<Score> updatedHighestScore = new ArrayList<>();
        Score newHighest = new Score("Diego", 56);
        updatedHighestScore.add(newHighest);
        achievementReport.updateHighestScoreInAGame(newHighest);
        Assertions.assertEquals(achievementReport.getTopScoreInAGame(), updatedHighestScore);
    }

    @Test
    public void should_add_to_highest_score_if_new_is_equal_to_prev_highest() throws JsonProcessingException {
        List<Score> updatedHighestScore = new ArrayList<>();
        updatedHighestScore.add(achievementReport.getTopScoreInAGame().get(0));
        Score newHighest = new Score("Diego", 50);
        updatedHighestScore.add(newHighest);
        achievementReport.updateHighestScoreInAGame(newHighest);
        Assertions.assertEquals(achievementReport.getTopScoreInAGame(), updatedHighestScore);
    }

    @Test
    public void should_update_was_highest_score_in_a_game() throws JsonProcessingException {
        List<Score> updatedWasHighestScore = new ArrayList<>();
        updatedWasHighestScore.add(achievementReport.getWasTopScoreInAGame().get(0));
        updatedWasHighestScore.add(achievementReport.getTopScoreInAGame().get(0));
        Score newHighest = new Score("Diego", 56);
        achievementReport.updateHighestScoreInAGame(newHighest);
        Assertions.assertEquals(achievementReport.getWasTopScoreInAGame(), updatedWasHighestScore);
    }

    @Test
    public void should_not_update_anything() {

    }
}
