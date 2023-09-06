package com.facu.altisima.service.api;

import com.facu.altisima.model.Game;

import java.util.List;

public interface GameServiceAPI {
    Game save (Game entity);

    void delete(String id);

    Game get(String id);

    List<Game> getAll();

    Game nextRound(String id);

    Game prevRound(String id);

    Game finishGame(String id);
}
