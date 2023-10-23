package com.facu.altisima.service.api;

import com.facu.altisima.model.AchievementReport;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.utils.ServiceResult;

public interface AchievementServiceAPI {
    ServiceResult<AchievementReport> getReport();
    ServiceResult<AchievementReport> update(Game game);
    ServiceResult<AchievementReport> save();
}
