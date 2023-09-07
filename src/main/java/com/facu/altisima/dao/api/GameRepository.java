package com.facu.altisima.dao.api;

import com.facu.altisima.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GameRepository extends MongoRepository<Game, String> {

}
