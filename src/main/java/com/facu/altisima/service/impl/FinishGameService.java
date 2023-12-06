package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.FinishedGameDto;
import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.controller.dto.PlayerRoundDto;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.repository.UserRepository;
import com.facu.altisima.service.utils.ServiceResult;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FinishGameService {
    AchievementService achievementService;
    private final PlayerRepository playerRepository;
    private final UserRepository userRepository;
    public Game finish(Game game, List<PlayerRoundDto> roundResults){
        String host = SecurityContextHolder.getContext().getAuthentication().getName();
        Game updatedGameState = game.nextRound(roundResults);
        String winner = getWinner(updatedGameState.getCurrentResults());
        assignResultsToPlayers(game, host, winner);
        achievementService.update(game);
        return updatedGameState;
    }

    private String getWinner(List<PlayerResultDto> finalTable){
        PlayerResultDto winner = Collections.max(finalTable, Comparator.comparingInt(PlayerResultDto::getScore));
        return winner.getUsername();
    }
    private void assignResultsToPlayers(Game game, String host, String winner) {
        List<PlayerResultDto> finalResults = game.getCurrentResults();
        for (PlayerResultDto playerResults : finalResults) {
            Optional<Player> optionalPlayer = playerRepository.findByUsername(playerResults.getUsername());
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
