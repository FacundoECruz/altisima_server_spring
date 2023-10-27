package com.facu.altisima.service.api;

import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.ServiceResult;

import java.util.List;

public interface UserServiceAPI {

    ServiceResult<User> save(User user);
    ServiceResult<User> associate(User user);
    void delete(String id);

    ServiceResult<User> get(String username);

    ServiceResult<List<User>> getAll();

    ServiceResult<User> put(EditUser userChanges);

    ServiceResult<User> login(LoginRequest loginRequest);
}
