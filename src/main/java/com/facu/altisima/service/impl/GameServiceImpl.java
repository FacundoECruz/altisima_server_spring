package com.facu.altisima.service.impl;

import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameServiceAPI {

    @Autowired
    private GameRepository gameRepository;

    public Game save(Game entity) {
        return gameRepository.save(entity);
    }

    public void delete(String id) {
        gameRepository.deleteById(id);
    }

    public Game get(String id) {
        Optional<Game> obj = gameRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }

    public List<Game> getAll() {
        List<Game> returnList = new ArrayList<>();
        gameRepository.findAll().forEach(obj -> returnList.add(obj));
        return returnList;
    }

    public Game nextRound(String id) {
        return null;
    }

    public Game prevRound(String id) {
        return null;
    }

    public Game finishGame(String id) {
        return null;
    }
}
