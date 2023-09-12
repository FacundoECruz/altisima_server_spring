package com.facu.altisima.service.api;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerServiceAPI {

        ServiceResult<Player> save (Player entity);

        ServiceResult<Player> get(String username);

        ServiceResult<List<Player>> getAll();

}
