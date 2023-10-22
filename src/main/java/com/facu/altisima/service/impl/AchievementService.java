package com.facu.altisima.service.impl;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.service.api.AchievementServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public class AchievementService implements AchievementServiceAPI {

    @Autowired
    private AchievementRepository achievementRepository;
    @Override
    public AchievementReport getReport(){
        List<AchievementReport> report = achievementRepository.findAll();
        // aca tiene que pasar la magiaaaaa
        return report.get(0);
    }

    @Override
    public void update(Game game){

    }
}
