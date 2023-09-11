package com.facu.altisima.service.impl;

import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerServiceAPI {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public ServiceResult<List<Player>> getAll() {
        List<Player> allPlayers = playerRepository.findAll();

        if (allPlayers != null) {
            return ServiceResult.success(allPlayers);
        } else {
            return ServiceResult.error("No se pudieron recuperar los jugadores");
        }
    }
    @Override
    public Player save(Player entity) {
        return playerRepository.save(entity);
    }

    @Override
    public Player get(String id) {
        Optional<Player> obj = playerRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }
    @Override
    public void delete(String id) {
        playerRepository.deleteById(id);
    }

    public Player put(String id, Player playerChanges) {
       Optional <Player> object = playerRepository.findById(id);
       Player obj = object.get();
        obj.setUsername(playerChanges.getUsername());
        obj.setImage(playerChanges.getImage());

        return playerRepository.save(obj);
    }
}
