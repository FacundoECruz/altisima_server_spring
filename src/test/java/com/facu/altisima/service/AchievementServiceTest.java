package com.facu.altisima.service;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.model.*;
import com.facu.altisima.service.impl.AchievementService;
import com.facu.altisima.utils.GameGenerator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AchievementServiceTest {
    public static final int INSIGNIFICANT_NUMBER = 5;
    AchievementService achievementService = new AchievementService();

    AchievementReport achievementReport;
    Integer totalRounds = 9;
    Game game;
    @BeforeEach
    public void setup() {
        generateFinishedGame();
        generateAchievmentReport();
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
        game = gameGenerator.generate(players, totalRounds);
        List<PlayerResultDto> finalResults = new ArrayList<>();
        generateFinalResults(players, finalResults);
        game.setCurrentResults(finalResults);
    }

    private void generateFinalResults(List<String> playersList, List<PlayerResultDto> finalResults) {
        for (int i = 0; i < playersList.size(); i++) {
            List<Integer> genericHistory = new ArrayList<>();
            for (int j = 0; j < totalRounds; j++) {
                genericHistory.add(INSIGNIFICANT_NUMBER);
            }
            PlayerResultDto playerResult = new PlayerResultDto(playersList.get(i), (i + 5) * 7, genericHistory);
            finalResults.add(playerResult);
        }
    }

    private void generateAchievmentReport() {
        generateTop3();
        makeHighestScoreInAGame();
        makeWasHighestScoreInAGame();
        makeScoredTenOrMoreInARound();
        makeHighestScoreInARound();
    }

    private static List<Score> makeHighestScoreInARound() {
        List<Score> highestScoreInARound = new ArrayList<>();
        Score highestInARound = new Score("Chaky", 11);
        highestScoreInARound.add(highestInARound);
        return highestScoreInARound;
    }

    private static List<String> makeScoredTenOrMoreInARound() {
        List<String> scoredTenOrMoreInARound = new ArrayList<>();
        scoredTenOrMoreInARound.add("Antone");
        return scoredTenOrMoreInARound;
    }

    private static List<Score> makeWasHighestScoreInAGame() {
        List<Score> wasTopScoreInAGame = new ArrayList<>();
        Score wasTopScore = new Score("Cristiano", 49);
        wasTopScoreInAGame.add(wasTopScore);
        return wasTopScoreInAGame;
    }

    private static List<Score> makeHighestScoreInAGame() {
        List<Score> topScoreInAGame = new ArrayList<>();
        Score topInAGame = new Score("Messi", 50);
        topScoreInAGame.add(topInAGame);
        return topScoreInAGame;
    }

    private static void generateTop3() {
        List<PlayerInTop> top3 = new ArrayList<>();
        PlayerInTop player1 = new PlayerInTop("Messi", 8, 654);
        PlayerInTop player2 = new PlayerInTop("Cristiano", 6, 492);
        PlayerInTop player3 = new PlayerInTop("Mbappe", 3, 214);
        top3.add(player1);
        top3.add(player2);
        top3.add(player3);
    }
    @Test
    public void should_return_the_updated_achievements() {
        AchievementReport expectedReport = new AchievementReport(top3, topScoreInAGame, wasTopScoreInAGame, scoredTenOrMoreInARound, highestScoreInARound);
        achievementService.update(game);
        AchievementReport report = achievementService.getReport();
        Assertions.assertEquals(report, expectedReport);
    }

    // Esto es una banda, se puede dividir en test mas pequeños, por ejemplo:
    // - Un test para el top#1
    // - Un test para el top#3
    // - Un test para el topScore

    @Test
    private void should_update_the_top1(){
        achievementService.update(game);
        AchievementReport report = achievementService.getReport();
        // en algun momento va a llamar al repository para pedir
        // el reporte, vamos a tener que mockear esa llamada,
        // asi por el momento no nos preocupamos de la coleccion en la
        // base de datos.
    }
    @Test
    public void should_return_the_achievements_of_a_given_player() {

    }

    @Test
    public void should_response_with_no_achievements_when_player_does_not_have_any() {

    }
}
