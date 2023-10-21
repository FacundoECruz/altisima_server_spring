package com.facu.altisima.service.impl;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.service.api.AchievementServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AchievementService implements AchievementServiceAPI {

    @Autowired
    private AchievementRepository achievementRepository;
    @Override
    public AchievementReport getReport(String id){
        Optional<AchievementReport> report = achievementRepository.findById(id);
    }

    @Override
    public void update(Game game){

    }
}
