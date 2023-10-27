package com.facu.altisima.service.utils;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.PlayerInTop;
import com.facu.altisima.model.Score;

import java.util.ArrayList;
import java.util.List;

public class FirstReport {
    public static AchievementReport generate() {
        List<PlayerInTop> top3 = makeTop3();
        List<Score> topScoreInAGame = makeTopScoreInAGame();
        List<Score> wasTopScoreInAGame = makeWasTopScoreInAGame();
        List<String> tenOrMoreInARound = makeScoredTenOrMoreInARound();
        List<Score> highestScoreInARound = makeHighestScoreInARound();
        return new AchievementReport(top3,
                topScoreInAGame,
                wasTopScoreInAGame,
                tenOrMoreInARound,
                highestScoreInARound);
    }

    private static List<Score> makeHighestScoreInARound() {
        List<Score> highestInARound = new ArrayList<>();
        Score highestRound = new Score("Fonzo", 11);
        highestInARound.add(highestRound);
        return highestInARound;
    }

    private static List<PlayerInTop> makeTop3(){
        List<PlayerInTop> top3 = new ArrayList<>();
        PlayerInTop player1 = new PlayerInTop("Facu", 6, 652);
        PlayerInTop player2 = new PlayerInTop("Antone", 3, 468);
        PlayerInTop player3 = new PlayerInTop("Bren", 3, 449);
        top3.add(player1);
        top3.add(player2);
        top3.add(player3);
        return top3;
    }

    private static List<Score> makeTopScoreInAGame() {
        List<Score> highest = new ArrayList<>();
        Score highestScoreInAGame = new Score("Clarii", 55);
        highest.add(highestScoreInAGame);
        return highest;
    }

    private static List<Score> makeWasTopScoreInAGame() {
        List<Score> wasTopScore = new ArrayList<>();
        Score wasHigh1 = new Score("Antone", 51);
        Score wasHigh2 = new Score("Nehui", 51);
        Score wasHigh3 = new Score("Bren", 52);
        wasTopScore.add(wasHigh1);
        wasTopScore.add(wasHigh2);
        wasTopScore.add(wasHigh3);
        return wasTopScore;
    }

    private static List<String> makeScoredTenOrMoreInARound() {
        List<String> tenOrMore = new ArrayList<>();
        tenOrMore.add("Fonzo");
        tenOrMore.add("Antone");
        return tenOrMore;
    }
}
