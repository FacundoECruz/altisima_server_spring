package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserServiceAPI {

    ServiceResult<User> save (User entity);

    void delete(String id);

    ServiceResult<User> get(String id);

    ServiceResult<List<User>> getAll();

    ServiceResult<User> put(String id, User userChanges);


    ServiceResult<User> login(LoginRequest loginRequest);
}
