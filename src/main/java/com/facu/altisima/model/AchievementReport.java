package com.facu.altisima.model;

import java.util.List;
import java.util.Objects;

public class AchievementReport {
    private List<PlayerInTop> top3;
    private List<Score> topScoreInAGame;
    private List<Score> wasTopScoreInAGame;
    private List<String> scoredTenOrMoreInARound;
    private List<Score> highestScoreInARound;

    public AchievementReport(List<PlayerInTop> top3, List<Score> topScoreInAGame, List<Score> wasTopScoreInAGame, List<String> scoredTenOrMoreInARound, List<Score> highestScoreInARound) {
        this.top3 = top3;
        this.topScoreInAGame = topScoreInAGame;
        this.wasTopScoreInAGame = wasTopScoreInAGame;
        this.scoredTenOrMoreInARound = scoredTenOrMoreInARound;
        this.highestScoreInARound = highestScoreInARound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AchievementReport that = (AchievementReport) o;
        return Objects.equals(top3, that.top3) && Objects.equals(topScoreInAGame, that.topScoreInAGame) && Objects.equals(wasTopScoreInAGame, that.wasTopScoreInAGame) && Objects.equals(scoredTenOrMoreInARound, that.scoredTenOrMoreInARound) && Objects.equals(highestScoreInARound, that.highestScoreInARound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(top3, topScoreInAGame, wasTopScoreInAGame, scoredTenOrMoreInARound, highestScoreInARound);
    }
}
