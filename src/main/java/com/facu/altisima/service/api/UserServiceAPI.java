package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;

import java.util.List;

public interface UserServiceAPI {

    ServiceResult<User> save(User entity);

    void delete(String id);

    ServiceResult<User> get(String username);

    ServiceResult<List<User>> getAll();

    ServiceResult<User> put(String username, User userChanges);

    ServiceResult<User> login(LoginRequestDto loginRequestDto);
}
