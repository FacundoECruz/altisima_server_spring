package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.utils.ServiceResult;

import java.util.List;
import java.util.UUID;

public interface GameServiceAPI {

    Game nextRound(String id, List<PlayerRound> roundResults);

    List<PlayerRound> prevRound(String id);

    Game finishGame(String id);

    ServiceResult<Game> createGame(List<String> players, Integer totalRounds);

    ServiceResult<Game> getGame(String id);

    ServiceResult<List<Game>> getAllGames();

    void delete(String id);
}
