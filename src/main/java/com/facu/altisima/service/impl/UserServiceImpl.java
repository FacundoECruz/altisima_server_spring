package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.repository.PlayerRepository;
import com.facu.altisima.repository.UserRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;

import com.facu.altisima.service.utils.ServiceResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceAPI {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public ServiceResult<List<User>> getAll() {
        List<User> users = userRepository.findAll();
        if (users.size() > 0) {
            return ServiceResult.success(users);
        } else {
            return ServiceResult.error("No se encontraron usuarios");
        }
    }

    @Override
    public ServiceResult<User> save(User user) {
        Optional<User> dbUser = userRepository.findByUsername(user.getUsername());
        Optional<Player> dbPlayer = playerRepository.findByUsername(user.getUsername());
        if (exists(dbUser) || exists(dbPlayer)) {
            return ServiceResult.error("El nombre de usuario ya existe");
        } else {
            User savedUser = userRepository.save(user);
            return ServiceResult.success(savedUser);
        }
    }

    private <A> Boolean exists(Optional<A> opt) {
        if (opt.isPresent())
            return true;
        else
            return false;
    }

    @Override
    public ServiceResult<User> associate(User user) {
        Optional<Player> player = playerRepository.findByUsername(user.getUsername());
        if (player.isEmpty()) {
            return ServiceResult.error("No se encontro jugador con ese nombre");
        } else {
            Player updatedPlayer = update(player.get(), user.getImage());
            playerRepository.save(updatedPlayer);
            User savedUser = userRepository.save(user);
            return ServiceResult.success(savedUser);
        }
    }

    private Player update(Player player, String imageUrl) {
        player.setImage(imageUrl);
        return player;
    }

    @Override
    public ServiceResult<User> get(String username) {
        Optional<User> retrievedUser = userRepository.findByUsername(username);
        if (exists(retrievedUser))
            return ServiceResult.success(retrievedUser.get());
        else
            return ServiceResult.error("El nombre de usuario no existe");

    }

    @Override
    public void delete(String username) {
        Optional<User> userToDelete = userRepository.findByUsername(username);
        if (userToDelete.isPresent()) {
            userRepository.deleteByUsername(username);
        }
    }

    public ServiceResult<User> put(EditUser userChanges) {
        Optional<User> user = userRepository.findByUsername(userChanges.getUsername());
        if (user.isPresent()) {
            User editedUser = editUser(user.get(), userChanges);
            userRepository.save(editedUser);
            return ServiceResult.success(editedUser);
        } else {
            return ServiceResult.error("El nombre de usuario no existe");
        }
    }

    private User editUser(User userToEdit, EditUser userChanges) {
        userToEdit.apply(userChanges);
        editPlayer(userChanges);
        return userToEdit;
    }

    private void editPlayer(EditUser userChanges) {
        Optional<Player> player = playerRepository.findByUsername(userChanges.getUsername());
        if (player.isPresent()) {
            Player playerToEdit = player.get();
            playerToEdit.apply(userChanges.getImage());
            playerRepository.save(playerToEdit);
        }
    }

    public ServiceResult<User> login(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        if (user.isPresent()) {
            return loginUser(loginRequest, user);
        } else
            return ServiceResult.error("Usuario no encontrado");
    }

    @NotNull
    private static ServiceResult<User> loginUser(LoginRequest loginRequest, Optional<User> user) {
        try {
            loginRequest.validate(user.get());
            return ServiceResult.success(user.get());
        } catch (RuntimeException e) {
            return ServiceResult.error(e.getMessage());
        }
    }
}
