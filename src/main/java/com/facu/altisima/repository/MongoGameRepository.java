package com.facu.altisima.repository;

import com.facu.altisima.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGameRepository extends
        GameRepository, MongoRepository<Game, String> {
}
