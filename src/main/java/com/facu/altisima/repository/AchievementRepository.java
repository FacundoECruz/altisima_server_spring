package com.facu.altisima.repository;

import com.facu.altisima.model.AchievementReport;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AchievementRepository extends MongoRepository<AchievementReport, String> {

}
