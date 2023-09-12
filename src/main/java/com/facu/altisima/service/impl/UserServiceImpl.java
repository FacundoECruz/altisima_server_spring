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
        if (users.size() > 0) {
            return ServiceResult.success(users);
        } else {
            return ServiceResult.error("No se encontraron usuarios.");
        }
    }

    @Override
    public ServiceResult<User> save(User user) {

        Optional<User> dbUser = userRepository.findByUsername(user.getUsername());

        if (dbUser.isPresent()) {
            return ServiceResult.error("El nombre de usuario ya existe");
        } else {
            User savedUser = userRepository.save(user);
            return ServiceResult.success(savedUser);
        }
    }

    @Override
    public ServiceResult<User> get(String username) {
        Optional<User> retrievedUser = userRepository.findByUsername(username);
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


    public ServiceResult<User> put(String username, User userChanges) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User userToChange = user.get();
            userToChange.setUsername(userChanges.getUsername());
            userToChange.setImage(userChanges.getImage());
            userToChange.setPassword(userChanges.getPassword());
            User changedUser = userRepository.save(userToChange);
            return ServiceResult.success(changedUser);
        } else {
            return ServiceResult.error("El nombre de usuario no existe");
        }
    }

    public ServiceResult<User> login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (user.isPresent()) {
            User userToLogin = user.get();
            if (userToLogin.getPassword() == loginRequest.getPassword()) {
                return ServiceResult.success(userToLogin);
            } else {
                return ServiceResult.error("Usuario o contraseña invalidos");
            }
        } else {
            return ServiceResult.error("No se encontraron usuarios");
        }
    }

}
