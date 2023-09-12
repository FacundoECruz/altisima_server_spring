package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.utils.GameGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameServiceAPI {

    @Autowired
    private GameRepository gameRepository;

    public ServiceResult<Game> createGame(List<String> players, Integer totalRounds) {
        GameGenerator gameGenerator = new GameGenerator();
        Game game = new Game(UUID.randomUUID(), new Date(), 1, gameGenerator.generateCardsPerRound(players.size(), totalRounds), players, gameGenerator.generateRoundResults(players), totalRounds);

        Game savedGame = gameRepository.save(game);

        return ServiceResult.success(savedGame);
    }

    public ServiceResult<List<Game>> getAllGames() {
        List<Game> allGames = gameRepository.findAll();
        if (allGames.size() > 0) {
            return ServiceResult.success(allGames);
        } else {
            return ServiceResult.error("No se encontraron partidas");
        }
    }

    @Override
    public ServiceResult<Game> getGame(String id) {
        Optional<Game> game = gameRepository.findById(id);
        if (game.isPresent()) {
            return ServiceResult.success(game.get());
        } else {
            return ServiceResult.error("No se encontro partida con ese id");
        }

    }

    @Override
    public void delete(String id) {
        gameRepository.deleteById(id);
    }


    public Game nextRound(String id, List<PlayerRound> roundResults) {
        return null;
    }

    public List<PlayerRound> prevRound(String id) {
        return null;
    }

    public Game finishGame(String id) {
        return null;
    }
}
