package com.facu.altisima.dao.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.facu.altisima.model.Player;

public interface PlayerRepository extends MongoRepository<Player, String>{

}
