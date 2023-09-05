package com.facu.altisima.service.api;

import com.facu.altisima.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerServiceAPI {
    CrudRepository<Player, String> getDao();

    Player save (Player entity);

    void delete(String id);

    Player get(String id);

    List<Player> getAll();

    Player put(String id, Player playerChanges);
}
