package com.facu.altisima.repository;

import com.facu.altisima.repository.dto.GameDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoLegacyGameRepository extends MongoRepository<GameDto, String> {
}
