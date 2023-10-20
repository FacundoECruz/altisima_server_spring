package com.facu.altisima.repository;

import com.facu.altisima.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AchievementRepository extends MongoRepository<Player, String> {

}
