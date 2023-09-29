package com.facu.altisima.repository;

import com.facu.altisima.model.Game;

import java.util.List;
import java.util.Optional;

public interface GameRepository {
    List<Game> findAll();
    Game save(Game game);
    Optional<Game> findById(String id);
    void deleteById(String id);
}
