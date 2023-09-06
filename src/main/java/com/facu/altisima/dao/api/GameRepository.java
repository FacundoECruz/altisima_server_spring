package com.facu.altisima.dao.api;

import com.facu.altisima.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
