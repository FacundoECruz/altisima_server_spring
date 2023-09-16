package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
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
            Game game = new Game(idGenerator.generate(), dateFormatter.formatDate(new Date()), 1, generate.cardsPerRound(players.size(), totalRounds), players, generate.roundResults(players), generate.roundBids(players), totalRounds);

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
        if (gameToDelete.isPresent()) {
            gameRepository.deleteById(id);
            return ServiceResult.success("Exitosamente borrado");
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }


    public ServiceResult<Game> nextRound(String id, List<PlayerRoundDto> roundResults) {
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if (retrievedGameFromDb.isPresent()) {
            Game game = retrievedGameFromDb.get();

            if(game.getCurrentRound() > game.getTotalRounds()){
                return ServiceResult.error("La partida ya esta terminada");
            }

            game.setLastBidsRound(roundResults);
            game.setCurrentRound(game.getCurrentRound() + 1);

            List<PlayerResultDto> currentResults = game.getCurrentResults();
            List<PlayerResultDto> updatedResults = new ArrayList<>();

            for (int i = 0; i < currentResults.size(); i++) {
                PlayerRoundDto playerRoundDto = roundResults.get(i);
                PlayerResultDto playerResultDto = currentResults.get(i);

                if (playerRoundDto.getUsername().equals(playerResultDto.getUsername())) {
                    if (playerRoundDto.getBidsLost() == 0) {
                        playerResultDto.setScore(playerResultDto.getScore() + playerRoundDto.getBid());
                    } else {
                        playerResultDto.setScore(playerResultDto.getScore() - playerRoundDto.getBidsLost());
                    }
                } else {
                    return ServiceResult.error("Los nombres de los players no coinciden");
                }
                updatedResults.add(playerResultDto);
            }
            game.setCurrentResults(updatedResults);

            Game updatedGame = gameRepository.save(game);
            return ServiceResult.success(updatedGame);
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    public ServiceResult<List<PlayerRoundDto>> prevRound(String id) {
        return null;
    }

    public Game finishGame(String id) {
        return null;
    }
}
