package com.facu.altisima.model;

import com.facu.altisima.controller.dto.PlayerResultDto;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Top3 {
    PlayerRepository playerRepository;
    List<PlayerInTop> top3;
    private boolean hadChanges;
    public Top3(List<PlayerInTop> top3, PlayerRepository playerRepository) {
        this.top3 = top3;
        this.playerRepository = playerRepository;
    }

    public ServiceResult<List<PlayerInTop>> check(List<PlayerResultDto> players) {
        for (int i = 0; i < players.size(); i++) {
            Optional<Player> playerState = playerRepository.findByUsername(
                    players.get(i).getUsername());
            if (playerState.isPresent()) {
                contrastPlayerWithTop3(playerState);
            }
        }
        if(hadChanges)
            return ServiceResult.success(top3);
        else
            return ServiceResult.error("No hubo cambios en el top 3");
    }

    private void contrastPlayerWithTop3(Optional<Player> playerState) {
        Player player = playerState.get();
        boolean isInTop3 = false;
        for(int j = 0; j < top3.size(); j++){
            PlayerInTop playerInTop = top3.get(j);
            if(player.getUsername().equals(playerInTop.getUsername())){
                isInTop3 = true;
                playerInTop.setGamesWon(player.getGamesWon());
                playerInTop.setTotalScore(player.getTotalScore());
                hadChanges = true;
            }
        }
        if(!isInTop3 && (player.getGamesWon() > top3.get(2).getGamesWon()
                || Objects.equals(player.getGamesWon(), top3.get(2).getGamesWon())
                && player.getTotalScore() > top3.get(2).getTotalScore())){
            top3.add(new PlayerInTop(player.getUsername(), player.getGamesWon(), player.getTotalScore()));
            hadChanges = true;
        }

        top3.sort(Collections.reverseOrder());
        if(top3.size() > 3)
            top3.remove(3);
    }
}
