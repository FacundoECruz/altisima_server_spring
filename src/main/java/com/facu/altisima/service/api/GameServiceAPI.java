package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.controller.dto.PlayerRound;
import com.facu.altisima.model.Game;
import com.facu.altisima.model.Player;

import java.util.List;

public interface GameServiceAPI {

    Game nextRound(String id, List<PlayerRound> roundResults);

    List<PlayerRound> prevRound(String id);

    Game finishGame(String id);

    Game createGame(List<String> players);

    Game getGame(String id);

    Game saveGame(Game game);

    List<Game> getAllGames();

    void delete(String id);
}
