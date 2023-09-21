package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.legacyDtos.CreateUserDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
public class UserControllerLegacy {
    @Autowired
    private UserServiceAPI userService;

    //CREATE USER
    @PostMapping
    public ResponseEntity<?> saveUserV1(@RequestBody CreateUserDto userData) {
        User user = new User(userData.getUsername(), userData.getEmail(), userData.getImage(), userData.getPassword(), 0);
        ServiceResult<User> serviceUser = userService.save(user);
        return new ResponseEntity<>(serviceUser.getData(), HttpStatus.OK);
    }


}
