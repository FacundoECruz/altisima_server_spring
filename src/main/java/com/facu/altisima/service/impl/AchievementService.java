package com.facu.altisima.service.impl;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerInTop;
import com.facu.altisima.model.Score;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.service.api.AchievementServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AchievementService implements AchievementServiceAPI {

    @Autowired
    private AchievementRepository achievementRepository;

    private GameRepository gameRepository;
    @Override
    public AchievementReport getReport(){
        List<AchievementReport> report = achievementRepository.findAll();
        // aca tiene que pasar la magiaaaaa
        return report.get(0);
    }

    @Override
    public void update(Game game){

    }
    private void save(){
        List<PlayerInTop> top3 = makeTop3();
        List<Score> topScoreInAGame = makeTopScoreInAGame();
        List<Score> wasTopScoreInAGame = makeWasTopScoreInAGame();
        List<String> tenOrMoreInARound = makeScoredTenOrMoreInARound();
        List<Score> highestScoreInARound = makeHighestScoreInARound();
        AchievementReport report = new AchievementReport(top3,
                topScoreInAGame,
                wasTopScoreInAGame,
                tenOrMoreInARound,
                highestScoreInARound);
    }

    private List<Score> makeHighestScoreInARound() {
        List<Score> highestInARound = new ArrayList<>();
        Score highestRound = new Score("Fonzo", 11);
        highestInARound.add(highestRound);
        return highestInARound;
    }

    private List<PlayerInTop> makeTop3(){
        List<PlayerInTop> top3 = new ArrayList<>();
        PlayerInTop player1 = new PlayerInTop("Facu", 6, 652);
        PlayerInTop player2 = new PlayerInTop("Antone", 3, 468);
        PlayerInTop player3 = new PlayerInTop("Bren", 3, 449);
        top3.add(player1);
        top3.add(player2);
        top3.add(player3);
        return top3;
    }

    private List<Score> makeTopScoreInAGame() {
        List<Score> highest = new ArrayList<>();
        Score highestScoreInAGame = new Score("Clarii", 55);
        highest.add(highestScoreInAGame);
        return highest;
    }

    private List<Score> makeWasTopScoreInAGame() {
        List<Score> wasTopScore = new ArrayList<>();
        Score wasHigh1 = new Score("Antone", 51);
        Score wasHigh2 = new Score("Newi", 51);
        Score wasHigh3 = new Score("Bren", 52);
        wasTopScore.add(wasHigh1);
        wasTopScore.add(wasHigh2);
        wasTopScore.add(wasHigh3);
        return wasTopScore;
    }

    private List<String> makeScoredTenOrMoreInARound() {
        List<String> tenOrMore = new ArrayList<>();
        tenOrMore.add("Fonzo");
        tenOrMore.add("Antone");
        return tenOrMore;
    }
}
