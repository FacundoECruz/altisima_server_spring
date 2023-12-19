package com.facu.altisima.service.api;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerData;
import com.facu.altisima.model.PlayerPerformance;

import java.util.List;

public interface NormalizeDataServiceAPI {
    List<PlayerData> normalizeAndUpdatePlayersData(List<Game> allGames);
}
