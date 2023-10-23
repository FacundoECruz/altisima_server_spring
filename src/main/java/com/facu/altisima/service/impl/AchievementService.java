package com.facu.altisima.service.impl;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.service.api.AchievementServiceAPI;
import com.facu.altisima.service.utils.FirstReport;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AchievementService implements AchievementServiceAPI {

    @Autowired
    private final AchievementRepository achievementRepository;

    private final GameRepository gameRepository;

    @Autowired
    public AchievementService(AchievementRepository achievementRepository, GameRepository gameRepository){
        this.achievementRepository = achievementRepository;
        this.gameRepository = gameRepository;
    }
    @Override
    public ServiceResult<AchievementReport> getReport(){
        List<AchievementReport> report = achievementRepository.findAll();
        if(report.isEmpty()){
            return ServiceResult.error("No se encontro el reporte");
        } else {
            return ServiceResult.success(report.get(report.size() - 1));
        }
    }

    @Override
    public void update(Game game){

    }
    public ServiceResult<AchievementReport> save(){
        AchievementReport report = FirstReport.generate();
        AchievementReport savedReport = achievementRepository.save(report);
        return ServiceResult.success(savedReport);
    }


}
