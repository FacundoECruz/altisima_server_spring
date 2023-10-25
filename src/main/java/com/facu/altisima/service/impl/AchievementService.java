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

    HighestScore highestScore = new HighestScore();
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

    @Override
    public ServiceResult<AchievementReport> update(Game game) {
        List<AchievementReport> allReports = achievementRepository.findAll();
        AchievementReport prevReport = allReports.get(allReports.size() - 1);

        updateHighestScore(game, prevReport);
        updateTop3(game, prevReport);

        return ServiceResult.success(prevReport);
    }
    private void updateHighestScore(Game game, AchievementReport prevReport) {
        ServiceResult<Score> highScore = highestScore.check(game.getCurrentResults(),
                prevReport.getTopScoreInAGame());
        if (highScore.isSuccess())
            prevReport.updateHighestScoreInAGame(highScore.getData());
    }
    private void updateTop3(Game game, AchievementReport prevReport) {
        Top3 top3 = new Top3(prevReport.getTop3(), playerRepository);
        ServiceResult<List<PlayerInTop>> newTop3 = top3.check(game.getCurrentResults());
        if(newTop3.isSuccess())
            prevReport.updateTop3(newTop3.getData());
    }

    public ServiceResult<AchievementReport> save() {
        AchievementReport report = FirstReport.generate();
        AchievementReport savedReport = achievementRepository.save(report);
        return ServiceResult.success(savedReport);
    }


}
