package com.facu.altisima.dao.api;

import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>{
    Optional<User> findByUsername(String username);
    void deleteByUsername(String username);
}
