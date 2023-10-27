package com.facu.altisima.utils;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.PlayerInTop;
import com.facu.altisima.model.Score;

import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {
    public AchievementReport generate(){
        return generateAchievmentReport();
    }

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
}
