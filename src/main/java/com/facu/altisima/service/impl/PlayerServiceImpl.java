package com.facu.altisima.service.impl;

import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
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
    public CrudRepository<Player, String> getDao() {
        return playerRepository;
    }

    @Override
    public Player save(Player entity) {
        return getDao().save(entity);
    }

    @Override
    public void delete(String id) {
        getDao().deleteById(id);
    }

    @Override
    public Player get(String id) {
        Optional<Player> obj = getDao().findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }

    @Override
    public List<Player> getAll() {
        List<Player> returnList = new ArrayList<>();
        getDao().findAll().forEach(obj -> returnList.add(obj));
        return returnList;
    }
}
