package com.facu.altisima.service.impl;

import com.facu.altisima.model.Game;
import com.facu.altisima.model.PlayerData;
import com.facu.altisima.model.PlayerPerformance;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.repository.PlayerDataRepository;
import com.facu.altisima.service.api.DataServiceAPI;
import com.facu.altisima.service.api.NormalizeDataServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataService implements DataServiceAPI {
    private final PlayerDataRepository dataRepository;
    private final GameRepository gameRepository;
    private final NormalizeDataServiceAPI normalizeService;

    @Autowired
    public DataService(PlayerDataRepository dataRepository, GameRepository gameRepository, NormalizeDataServiceAPI normalizeService) {
        this.dataRepository = dataRepository;
        this.gameRepository = gameRepository;
        this.normalizeService = normalizeService;
    }

    @Override
    public ServiceResult<PlayerData> get(String username) {
        Optional<PlayerData> dataFromDb = dataRepository.findByUsername(username);
        if (dataFromDb.isPresent())
            return ServiceResult.success(dataFromDb.get());
        else
            return ServiceResult.error("player data could not be recovered");
    }

    @Override
    public ServiceResult<List<PlayerData>> getAll() {
        List<PlayerData> dataFromDb = dataRepository.findAll();
        if (!dataFromDb.isEmpty()) {
            return ServiceResult.success(dataFromDb);
        } else {
            return ServiceResult.error("players data could not be recovered");
        }
    }

    @Override
    public ServiceResult<String> update() {
        List<Game> allGames = gameRepository.findAll();
        if (allGames.isEmpty()) {
            return ServiceResult.error("Cannot find games");
        }
        List<PlayerData> updatedData = normalizeService.normalizeAndUpdatePlayersData(allGames);
        for(int i = 0; i < updatedData.size(); i++){
            System.out.println(updatedData.get(i).getUsername());
            System.out.println(updatedData.get(i).getWinnedRounds());
            System.out.println("*******************************");
            dataRepository.save(updatedData.get(i));
        }
        return ServiceResult.success("Updated players data");
    }
}
