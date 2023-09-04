package com.facu.altisima.service.impl;

import com.facu.altisima.commons.GenericServiceImpl;
import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.service.api.PlayerServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl extends GenericServiceImpl<Player, String> implements PlayerServiceAPI {

    @Autowired
    private PlayerRepository playerRepository;
    @Override
    public CrudRepository<Player, String> getDao() {
        return playerRepository;
    }
}
