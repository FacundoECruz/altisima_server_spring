package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.controller.dto.legacyDtos.GameIdDto;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.utils.ServiceResult;

import java.util.List;


public interface GameServiceAPI {

    ServiceResult<Game> nextRound(String id, List<PlayerRoundDto> roundResults);

    ServiceResult<Game> prevRound(String id);

    ServiceResult<Game> finishGame(FinishedGameDto finishedGameDto);

    ServiceResult<Game> createGame(List<String> players, Integer totalRounds);

    ServiceResult<Game> getGame(String id);

    ServiceResult<List<Game>> getAllGames();

    ServiceResult<String> delete(String id);
}
