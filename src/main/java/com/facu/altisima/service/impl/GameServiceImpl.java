package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.utils.DateFormatter;
import com.facu.altisima.service.utils.Generate;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameServiceAPI {
    private final GameRepository gameRepository;
    private final IdGenerator idGenerator;
    Generate generate = new Generate();
    DateFormatter dateFormatter = new DateFormatter();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, IdGenerator idGenerator) {
        this.gameRepository = gameRepository;
        this.idGenerator = idGenerator;
    }

    public ServiceResult<Game> createGame(List<String> players, Integer totalRounds) {
        if (players.size() > 8) {
            return ServiceResult.error("Demasiados jugadores");
        } else {
            Game game = new Game(idGenerator.generate(), dateFormatter.formatDate(new Date()), 1, generate.cardsPerRound(players.size(), totalRounds), players, generate.roundResults(players), totalRounds);

            Game savedGame = gameRepository.save(game);

            return ServiceResult.success(savedGame);
        }
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
    public ServiceResult<String> delete(String id) {
        Optional<Game> gameToDelete = gameRepository.findById(id);
        if(gameToDelete.isPresent()){
            gameRepository.deleteById(id);
            return ServiceResult.success("Exitosamente borrado");
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }


    public ServiceResult<GameState> nextRound(String id, List<PlayerRound> roundResults) {
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if(retrievedGameFromDb.isPresent()){
            Game game = retrievedGameFromDb.get();
            Integer nextRound = game.getCurrentRound() + 1;
            Integer cardsToDealInNextRound = game.getCardsPerRound().get(nextRound);
            List<PlayerRound> prevScores = game.getRoundResults();

            //....
            return ServiceResult.error("Todavia no termine");
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    public List<PlayerRound> prevRound(String id) {
        return null;
    }

    public Game finishGame(String id) {
        return null;
    }
}
