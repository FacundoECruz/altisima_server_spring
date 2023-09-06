package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.dao.api.UserRepository;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;

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
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public User get(String id) {
        Optional<User> obj = userRepository.findById(id);
        if (obj.isPresent()) {
            return obj.get();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> returnList = new ArrayList<>();
        userRepository.findAll().forEach(obj -> returnList.add(obj));
        return returnList;
    }

    public User put(String id, User userChanges) {
        Optional<User> object = userRepository.findById(id);
        User obj = object.get();
        obj.setUsername(userChanges.getUsername());
        obj.setImage(userChanges.getImage());
        obj.setPassword(userChanges.getPassword());

        return userRepository.save(obj);
    }

    public Optional<User> login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isEmpty()) {
            return Optional.empty();
        }
        if (user.get().getPassword().equals(loginRequest.getPassword())) {
            return user;
        }
        return Optional.empty();
    }

}
