package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.repository.AchievementRepository;
import com.facu.altisima.repository.GameRepository;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.repository.UserRepository;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.GameServiceAPI;
import com.facu.altisima.service.utils.DateFormatter;
import com.facu.altisima.service.utils.Generate;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameServiceAPI {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    private final IdGenerator idGenerator;
    private final AchievementRepository achievementRepository;
    private final AchievementService achievementService;
    private final FinishGameService finishGameService;
    Generate generate = new Generate();
    DateFormatter dateFormatter = new DateFormatter();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, UserRepository userRepository, IdGenerator idGenerator, AchievementRepository achievementRepository, FinishGameService finishGameService) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
        this.achievementRepository = achievementRepository;
        this.achievementService = new AchievementService(achievementRepository, gameRepository, playerRepository);
        this.finishGameService = finishGameService;

    }

    public ServiceResult<Game> createGame(List<String> players, Integer totalRounds) {
        if (players.size() > 8) {
            return ServiceResult.error("Demasiados jugadores");
        } else {
            List<String> playersImgs = getPlayersImages(players);
            List<String> playersIds = getPlayersIds(players);
            Game game = new Game(idGenerator.generate(), dateFormatter.formatDate(new Date()), 1, generate.cardsPerRound(players.size(), totalRounds), playersIds, generate.roundResults(players), generate.roundBids(players), totalRounds, playersImgs);
            Game savedGame = gameRepository.save(game);
            return ServiceResult.success(savedGame);
        }
    }

    private List<String> getPlayersImages(List<String> players) {
        return players.stream().map(player -> {
            Optional<Player> playerFromDb = playerRepository.findByUsername(player);
            if (playerFromDb.isPresent())
                return playerFromDb.get().getImage();
            else
                return null;
        }).collect(Collectors.toList());
    }

    private List<String> getPlayersIds(List<String> players) {
        return players.stream().map(player -> {
            Optional<Player> playerFromDb = playerRepository.findByUsername(player);
            if (playerFromDb.isPresent()) {
                return playerFromDb.get().get_id();
            } else
                return null;
        }).collect(Collectors.toList());
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
            if (game.isLastRound()) {
                Game finished = finishGameService.finish(game, roundResults);
                return ServiceResult.success(gameRepository.save(finished));
            }
            Game updatedGameState = game.nextRound(roundResults);
            return ServiceResult.success(gameRepository.save(updatedGameState));
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    public ServiceResult<Game> prevRound(String id) {
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if (retrievedGameFromDb.isPresent()) {
            Game game = retrievedGameFromDb.get();
            gameRepository.save(game.turnBackOneRound());
            return ServiceResult.success(game);
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }
}
