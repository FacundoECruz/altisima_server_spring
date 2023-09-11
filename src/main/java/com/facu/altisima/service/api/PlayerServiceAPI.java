package com.facu.altisima.service.api;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerServiceAPI {

        Player save (Player entity);

        void delete(String id);

        Player get(String id);

        ServiceResult<List<Player>> getAll();

        Player put(String id, Player playerChanges);
}
