package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.GameState;
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

    public Game nextRound(String id) {
        return null;
    }

    public Game prevRound(String id) {
        return null;
    }

    public Game finishGame(String id) {
        return null;
    }

    public Game createGame(List<String> players) {
        return null;
    }

    @Override
    public Game getGame(String id) {
        Optional<Game> obj = gameRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    public List<Game> getAllGames() {
        List<Game> returnList = new ArrayList<>();
        gameRepository.findAll().forEach(obj -> returnList.add(obj));
        return returnList;
    }

    @Override
    public void delete(String id) {
        gameRepository.deleteById(id);
    }

}
