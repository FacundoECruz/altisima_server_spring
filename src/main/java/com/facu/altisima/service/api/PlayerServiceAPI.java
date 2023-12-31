package com.facu.altisima.service.api;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface PlayerServiceAPI {

        ServiceResult<Player> save (Player entity) throws JsonProcessingException;

        ServiceResult<Player> get(String username);

        ServiceResult<List<Player>> getAll();

        ServiceResult<List<Player>> getUnregistered();

        ServiceResult<List<Player>> getAssociatedPlayers(String username);
}
