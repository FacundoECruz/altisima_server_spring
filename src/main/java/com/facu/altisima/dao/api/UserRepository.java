package com.facu.altisima.dao.api;

import com.facu.altisima.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>{

}
