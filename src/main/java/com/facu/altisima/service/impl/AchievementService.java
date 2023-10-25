package com.facu.altisima.service.impl;

import com.facu.altisima.model.*;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.service.api.AchievementServiceAPI;
import com.facu.altisima.service.utils.FirstReport;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AchievementService implements AchievementServiceAPI {
    @Autowired
    private final AchievementRepository achievementRepository;
    @Autowired
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    @Autowired
    public AchievementService(AchievementRepository achievementRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
        this.achievementRepository = achievementRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public ServiceResult<AchievementReport> getReport() {
        List<AchievementReport> report = achievementRepository.findAll();
        if (report.isEmpty()) {
            return ServiceResult.error("No se encontro el reporte");
        } else {
            return ServiceResult.success(report.get(report.size() - 1));
        }
    }
    public ServiceResult<AchievementReport> save() {
        AchievementReport report = FirstReport.generate();
        AchievementReport savedReport = achievementRepository.save(report);
        return ServiceResult.success(savedReport);
    }
    @Override
    public ServiceResult<AchievementReport> update(Game game) {
        List<AchievementReport> allReports = achievementRepository.findAll();
        AchievementReport prevReport = allReports.get(allReports.size() - 1);

        updateHighestScore(game, prevReport);
        updateTop3(game, prevReport);
        updateHighestRound(game, prevReport);
        //aca deberiamos guardar en la base de datos el reporte actualizado
        return ServiceResult.success(prevReport);
    }
    private void updateHighestScore(Game game, AchievementReport prevReport) {
        HighestScore highestScore = new HighestScore(prevReport.getTopScoreInAGame());
        checkHighestScoreUpdate(game, prevReport, highestScore);
    }

    private static void checkHighestScoreUpdate(Game game, AchievementReport prevReport, HighestScore highestScore) {
        ServiceResult<List<Score>> highScore = highestScore.check(game.getCurrentResults());
        if (highScore.isSuccess())
            prevReport.updateHighestScoreInAGame(highScore.getData());
    }

    private void updateTop3(Game game, AchievementReport prevReport) {
        Top3 top3 = new Top3(prevReport.getTop3(), playerRepository);
        checkTop3Update(game, prevReport, top3);
    }
    private static void checkTop3Update(Game game, AchievementReport prevReport, Top3 top3) {
        ServiceResult<List<PlayerInTop>> newTop3 = top3.check(game.getCurrentResults());
        if(newTop3.isSuccess())
            prevReport.updateTop3(newTop3.getData());
    }

    private void updateHighestRound(Game game, AchievementReport prevReport){
        HighestRound highestRound = new HighestRound(prevReport.getScoredTenOrMoreInARound(),
                    prevReport.getHighestScoreInARound(), game.getCurrentResults());
        checkTenOrMoreInARound(prevReport, highestRound);
        checkHighestScoreInARound(prevReport, highestRound);
    }
    private static void checkTenOrMoreInARound(AchievementReport prevReport, HighestRound highestRound) {
        ServiceResult<List<String>> tenOrMore = highestRound.checkTenOrMoreInARound();
        if(tenOrMore.isSuccess())
            prevReport.updateTenOrMoreInARound(tenOrMore.getData());
    }
    private static void checkHighestScoreInARound(AchievementReport prevReport, HighestRound highestRound) {
        ServiceResult<List<Score>> highestInARound = highestRound.checkHighestRound();
        if(highestInARound.isSuccess())
            prevReport.updateHighestScoreInARound(highestInARound.getData());
    }
}
