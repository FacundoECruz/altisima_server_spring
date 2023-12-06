package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.jwtTest.AuthResponse;
import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.controller.dto.legacyDtos.CreateUserDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface UserServiceAPI {

    AuthResponse save(CreateUserDto request);
    ServiceResult<User> associate(User user);
    void delete(String id);

    ServiceResult<User> get(String username);

    ServiceResult<List<User>> getAll();

    ServiceResult<User> put(EditUser userChanges);

    AuthResponse login(LoginRequest request) throws JsonProcessingException;
}
