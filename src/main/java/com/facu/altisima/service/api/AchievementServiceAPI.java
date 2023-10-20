package com.facu.altisima.service.api;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;

public interface AchievementServiceAPI {

    AchievementReport getReport();

    void update(Game game);
}
