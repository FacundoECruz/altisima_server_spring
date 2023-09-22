package com.facu.altisima.service.impl;

import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.dao.api.PlayerRepository;
import com.facu.altisima.dao.api.UserRepository;
import com.facu.altisima.model.Player;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;

import com.facu.altisima.service.utils.ServiceResult;
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
        //ACA HAY QUE CREAR EL PLAYER TAMBIEN
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
    public void delete(String username) {
        Optional<User> userToDelete = userRepository.findById(username);
        if (userToDelete.isPresent()) {
            userRepository.deleteByUsername(username);
        }
    }

    public ServiceResult<User> put(String username, EditUserDto userChanges) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            User userToChange = user.get();
            userToChange.setImage(userChanges.getImage());
            userToChange.setPassword(userChanges.getPassword());
            userRepository.save(userToChange);

            Optional<Player> player = playerRepository.findByUsername(userToChange.getUsername());
            if(player.isPresent()){
                Player playerToChange = player.get();
                playerToChange.setImage(userChanges.getImage());
                playerRepository.save(playerToChange);
            }
            return ServiceResult.success(userToChange);
        } else {
            return ServiceResult.error("El nombre de usuario no existe");
        }
    }

    public ServiceResult<User> login(LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findByUsername(loginRequestDto.getUsername());

        if (user.isPresent()) {
            User userToLogin = user.get();
            if (userToLogin.getPassword().equals(loginRequestDto.getPassword())) {
                return ServiceResult.success(userToLogin);
            } else {
                return ServiceResult.error("Usuario o contraseña invalidos");
            }
        } else {
            return ServiceResult.error("No se encontraron usuarios");
        }
    }

}
