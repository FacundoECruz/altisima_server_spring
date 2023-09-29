package com.facu.altisima.repository;

import com.facu.altisima.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.facu.altisima.model.Player;

import java.util.Optional;

public interface PlayerRepository extends MongoRepository<Player, String>{
    Optional<Player> findByUsername(String username);
}
