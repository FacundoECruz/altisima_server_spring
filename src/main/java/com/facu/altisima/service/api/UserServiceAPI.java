package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserServiceAPI {

    User save (User entity);

    void delete(String id);

    User get(String id);

    ServiceResult<List<User>> getAll();

    User put(String id, User userChanges);

    Optional<User> login(LoginRequest loginRequest);
}
