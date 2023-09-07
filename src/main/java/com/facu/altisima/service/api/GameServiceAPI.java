package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.GameState;
import com.facu.altisima.model.Game;

import java.util.List;

public interface GameServiceAPI {

    Game nextRound(String id);

    Game prevRound(String id);

    Game finishGame(String id);

    Game createGame(List<String> players);

    Game getGame(String id);

    Game saveGame(Game game);

    List<Game> getAllGames();

    void delete(String id);
}
