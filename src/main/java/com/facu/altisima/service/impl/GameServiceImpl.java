package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.dao.api.GameRepository;
import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.dao.api.UserRepository;
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


//    public ServiceResult<GameV1> createGameV1(List<PlayersRoundAndScoreDto> players){
//        if (players.size() > 8) {
//            return ServiceResult.error("Demasiados jugadores");
//        } else {
//            Integer totalRounds = 9;
//            List<List<PlayersRoundAndScoreDto>> results = new ArrayList<>();
//            results.add(players);
//
//            List<String> playersNames = new ArrayList<>();
//            for(int i = 0; i < players.size(); i++) {
//                playersNames.add(players.get(i).getUsername());
//            }
//
//            GameV1 gameV1 = new GameV1(generate.cardsPerRound(players.size(), totalRounds), results, dateFormatter.formatDate(new Date()), 1, playersNames);
//
//            GameV1 savedGameV1 = gameRepository.save(gameV1);
//
//            return ServiceResult.success(savedGameV1);
//        }
//    }
    public ServiceResult<Game> createGame(List<String> players, Integer totalRounds) {
        if (players.size() > 8) {
            return ServiceResult.error("Demasiados jugadores");
        } else {
            List<String> playersImgs = new ArrayList<>();
            for(int i = 0; i < players.size(); i++){
                Optional<Player> playerFromDb = playerRepository.findByUsername(players.get(i));
                if(playerFromDb.isPresent()){
                    playersImgs.add(playerFromDb.get().getImage());
                }
            }
            Game game = new Game(idGenerator.generate(), dateFormatter.formatDate(new Date()), 1, generate.cardsPerRound(players.size(), totalRounds), players, generate.roundResults(players), generate.roundBids(players), totalRounds, playersImgs);

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
        Integer baseScoreForWinning = 5;
        if (retrievedGameFromDb.isPresent()) {
            Game game = retrievedGameFromDb.get();

            if (game.getCurrentRound() > game.getTotalRounds()) {
                return ServiceResult.error("La partida ya esta terminada");
            }

            game.setLastBidsRound(roundResults);
            game.setCurrentRound(game.getCurrentRound() + 1);

            List<PlayerResultDto> resultsUntilThePrevRound = game.getCurrentResults();

            List<PlayerResultDto> updatedResults = new ArrayList<>();
            for (int i = 0; i < resultsUntilThePrevRound.size(); i++) {
                PlayerRoundDto playerRoundDto = roundResults.get(i);
                PlayerResultDto playerResultDto = resultsUntilThePrevRound.get(i);

                if (playerRoundDto.getUsername().equals(playerResultDto.getUsername())) {
                    if (playerRoundDto.getBidsLost() == 0) {
                        playerResultDto.setScore(playerResultDto.getScore() + playerRoundDto.getBid() + baseScoreForWinning);
                        playerResultDto.updateHistory(playerRoundDto.getBid() + baseScoreForWinning);
                    } else {
                        playerResultDto.setScore(playerResultDto.getScore() - playerRoundDto.getBidsLost());
                        playerResultDto.updateHistory(playerRoundDto.getBidsLost());
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
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if (retrievedGameFromDb.isPresent()) {
            List<PlayerRoundDto> prevRoundBids = retrievedGameFromDb.get().getLastBidsRound();
            return ServiceResult.success(prevRoundBids);
        } else {
            return ServiceResult.error("No se encontro la partida");
        }
    }

    public ServiceResult<String> finishGame(FinishedGameDto finishedGameDto) {
        String id = finishedGameDto.getId();
        String host = finishedGameDto.getHost();
        String winner = finishedGameDto.getWinner();
        Optional<Game> retrievedGameFromDb = gameRepository.findById(id);
        if (retrievedGameFromDb.isPresent()) {
            assignResultsToPlayers(retrievedGameFromDb.get(), host, winner);
            return ServiceResult.success("Se guardaron los datos de la partida");
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
            }
        }
        Optional<User> retrievedUserHost = userRepository.findByUsername(host);
        if (retrievedUserHost.isPresent()) {
            User user = retrievedUserHost.get();
            user.setCreatedGames(user.getCreatedGames() + 1);
        }

        Optional<Player> retrievedPlayer = playerRepository.findByUsername(winner);
        if (retrievedPlayer.isPresent()) {
            Player player = retrievedPlayer.get();
            player.setGamesWon(player.getGamesWon() + 1);
        }
    }
}
