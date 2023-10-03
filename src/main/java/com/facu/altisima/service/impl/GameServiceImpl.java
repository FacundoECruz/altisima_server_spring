package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
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
    Generate generate = new Generate();
    DateFormatter dateFormatter = new DateFormatter();

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, PlayerRepository playerRepository, UserRepository userRepository, IdGenerator idGenerator) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
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
        Integer baseScoreForWinning = 5;
        if (retrievedGameFromDb.isPresent()) {
            Game game = retrievedGameFromDb.get();
            if (game.getCurrentRound() > game.getTotalRounds()) {
                return ServiceResult.error("La partida ya esta terminada");
            }
            Game updatedGameState = game.nextRound(roundResults);

            Game updatedGame = gameRepository.save(updatedGameState);
            return ServiceResult.success(updatedGame);
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    public ServiceResult<Game> prevRound(String id) {
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if (retrievedGameFromDb.isPresent()) {
            Game game = turnBackOneRound(retrievedGameFromDb.get());
            gameRepository.save(game);
            return ServiceResult.success(game);
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    private static Game turnBackOneRound(Game game) {
        Integer prevRound = game.getCurrentRound() - 1;
        List<PlayerResultDto> prevRoundResults = new ArrayList<>();
        for (int i = 0; i < game.getPlayers().size(); i++) {
            PlayerResultDto prevPlayerScore = game.getCurrentResults().get(i).prevRoundState();
            prevRoundResults.add(prevPlayerScore);
        }
        return new Game(
                game.get_id(),
                game.getDate(),
                prevRound,
                game.getCardsPerRound(),
                game.getPlayers(),
                prevRoundResults,
                game.getLastBidsRound(),
                game.getTotalRounds(),
                game.getPlayersImgs());
    }

    public ServiceResult<Game> finishGame(FinishedGameDto finishedGameDto) {
        String id = finishedGameDto.getId();
        String host = finishedGameDto.getHost();
        String winner = finishedGameDto.getWinner();
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if (retrievedGameFromDb.isPresent()) {
            assignResultsToPlayers(retrievedGameFromDb.get(), host, winner);
            return ServiceResult.success(retrievedGameFromDb.get());
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    private void assignResultsToPlayers(Game game, String host, String winner) {
        List<PlayerResultDto> finalResults = game.getCurrentResults();
        for (int i = 0; i < finalResults.size(); i++) {
            PlayerResultDto playerResults = finalResults.get(i);
            Optional<Player> optionalPlayer = playerRepository.findByUsername(finalResults.get(i).getUsername());
            if (optionalPlayer.isPresent()) {
                Player player = optionalPlayer.get();
                player.setGamesPlayed(player.getGamesPlayed() + 1);
                player.setTotalScore(player.getTotalScore() + playerResults.getScore());
                playerRepository.save(player);
            }
        }
        Optional<User> retrievedUserHost = userRepository.findByUsername(host);
        if (retrievedUserHost.isPresent()) {
            User user = retrievedUserHost.get();
            user.setCreatedGames(user.getCreatedGames() + 1);
            userRepository.save(user);
        }

        Optional<Player> retrievedPlayer = playerRepository.findByUsername(winner);
        if (retrievedPlayer.isPresent()) {
            Player player = retrievedPlayer.get();
            player.setGamesWon(player.getGamesWon() + 1);
            playerRepository.save(player);
        }
    }
}
