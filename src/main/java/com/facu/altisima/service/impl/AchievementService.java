package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.Score;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.service.api.AchievementServiceAPI;
import com.facu.altisima.service.utils.FirstReport;
import com.facu.altisima.service.utils.ServiceResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AchievementService implements AchievementServiceAPI {

    @Autowired
    private final AchievementRepository achievementRepository;

    private final GameRepository gameRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, GameRepository gameRepository) {
        this.achievementRepository = achievementRepository;
        this.gameRepository = gameRepository;
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

        ServiceResult<Score> highScore = checkNewHigherScore(game.getCurrentResults(), prevReport.getTopScoreInAGame());
        if(highScore.isSuccess())
            prevReport.updateHighestScoreInAGame(highScore.getData());
        return ServiceResult.success(prevReport);
    }

    private ServiceResult<Score> checkNewHigherScore(List<PlayerResultDto> results, List<Score> currentHighest) {
        PlayerResultDto highScoreInCurrentGame = getHighScoreInGame(results);
        if (isNewRecord(currentHighest, highScoreInCurrentGame))
            return ServiceResult.success(highScoreInCurrentGame.toScore());
        else
            return ServiceResult.error("No hay nuevo record");
    }

    private static boolean isNewRecord(List<Score> currentHighest, PlayerResultDto highScoreInCurrentGame) {
        return highScoreInCurrentGame.getScore() >= currentHighest.get(0).getScore();
    }

    @NotNull
    private static PlayerResultDto getHighScoreInGame(List<PlayerResultDto> results) {
        List<Integer> fakeHistory = new ArrayList<>();
        PlayerResultDto highScoreInGame = new PlayerResultDto("void", 0, fakeHistory);
        for (PlayerResultDto result : results) {
            if (result.getScore() > highScoreInGame.getScore())
                highScoreInGame = result;
        }
        return highScoreInGame;
    }

    public ServiceResult<AchievementReport> save() {
        AchievementReport report = FirstReport.generate();
        AchievementReport savedReport = achievementRepository.save(report);
        return ServiceResult.success(savedReport);
    }


}
