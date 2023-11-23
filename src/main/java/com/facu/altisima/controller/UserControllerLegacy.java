package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.legacyDtos.CreateUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.PlayerServiceAPI;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "*")
public class UserControllerLegacy {
    @Autowired
    private UserServiceAPI userService;
    @Autowired
    private PlayerServiceAPI playerService;

    @PostMapping
    public ResponseEntity<?> saveUserV1(@RequestBody CreateUserDto userData) {
        try {
            ServiceResult<User> savedUser = saveUser(userData);
            return new Response().build(savedUser);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @NotNull
    private ServiceResult<User> saveUser(CreateUserDto userData) throws JsonProcessingException {
        User user = userData.toDomain();
        ServiceResult<User> saveUserResult = userService.save(user);
        playerService.save(user.toPlayer());
        return saveUserResult;
    }

    @PostMapping(value = "/associate")
    public ResponseEntity<?> associateUser(@RequestBody CreateUserDto createUserDto) {
        try{
            ServiceResult<User> associatedUser = associate(createUserDto);
            return new Response().build(associatedUser);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ServiceResult<User> associate(CreateUserDto associateDto) {
        User user = associateDto.toDomain();
        return userService.associate(user);
    }
}
