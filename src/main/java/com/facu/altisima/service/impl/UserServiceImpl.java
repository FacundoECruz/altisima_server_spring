package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.dao.api.UserRepository;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;

import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceAPI {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ServiceResult<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        if (users != null) {
            return ServiceResult.success(users);
        } else {
            return ServiceResult.error("No se encontraron usuarios.");
        }
    }

    @Override
    public ServiceResult<User> save(User entity) {
        User savedUser = userRepository.save(entity);
        if (savedUser != null) {
            return ServiceResult.success(savedUser);
        } else {
            return ServiceResult.error("El nombre de usuario ya existe");
        }
    }

    @Override
    public ServiceResult<User> get(String id) {
        Optional<User> retrievedUser = userRepository.findById(id);
        if (retrievedUser != null && retrievedUser.isPresent()) {
            return ServiceResult.success(retrievedUser.get());
        } else {
            return ServiceResult.error("El nombre de usuario no existe");
        }

    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }


    public ServiceResult<User> put(String id, User userChanges) {
        Optional<User> user = userRepository.findById(id);
        if (user != null && user.isPresent()) {
            User obj = user.get();
            obj.setUsername(userChanges.getUsername());
            obj.setImage(userChanges.getImage());
            obj.setPassword(userChanges.getPassword());
            User changedUser = userRepository.save(obj);
            return ServiceResult.success(changedUser);
        } else {
            return ServiceResult.error("El nombre de usuario no existe");
        }
    }

    public ServiceResult<User> login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (user != null) {
            return ServiceResult.success(user);
        } else {
            return ServiceResult.error("No se encontraron usuarios.");
        }
    }

}
