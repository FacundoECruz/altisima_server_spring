package com.facu.altisima.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.ALWAYS)
@Document("achievements")
public class AchievementReport {
    @Id
    private String _id;
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

    public AchievementReport(){

    }

    public void updateHighestScoreInAGame(List<Score> newHighest){
        boolean isOvercomeRecord = false;
        for(int i = 0; i < newHighest.size(); i++){
            for(int j = 0; j < topScoreInAGame.size(); j++) {
                if (Objects.equals(topScoreInAGame.get(j).getUsername(), newHighest.get(i).getUsername())) {
                    isOvercomeRecord = true;
                    break;
                }
            }
        }
        if(isOvercomeRecord) {
            wasTopScoreInAGame.addAll(topScoreInAGame);
        }
        topScoreInAGame = newHighest;
    }

    public void updateTop3(List<PlayerInTop> newTop3){
        top3 = newTop3;
    }

    public void updateTenOrMoreInARound(List<String> newListOfRecords){
        scoredTenOrMoreInARound = newListOfRecords;
    }

    public void updateHighestScoreInARound(List<Score> newRecord){
        highestScoreInARound = newRecord;
    }

    public List<Score> getTopScoreInAGame() {
        return topScoreInAGame;
    }

    public void setTopScoreInAGame(List<Score> topScoreInAGame) {
        this.topScoreInAGame = topScoreInAGame;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<PlayerInTop> getTop3() {
        return top3;
    }

    public void setTop3(List<PlayerInTop> top3) {
        this.top3 = top3;
    }

    public List<Score> getWasTopScoreInAGame() {
        return wasTopScoreInAGame;
    }

    public void setWasTopScoreInAGame(List<Score> wasTopScoreInAGame) {
        this.wasTopScoreInAGame = wasTopScoreInAGame;
    }

    public List<String> getScoredTenOrMoreInARound() {
        return scoredTenOrMoreInARound;
    }

    public void setScoredTenOrMoreInARound(List<String> scoredTenOrMoreInARound) {
        this.scoredTenOrMoreInARound = scoredTenOrMoreInARound;
    }

    public List<Score> getHighestScoreInARound() {
        return highestScoreInARound;
    }

    public void setHighestScoreInARound(List<Score> highestScoreInARound) {
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
