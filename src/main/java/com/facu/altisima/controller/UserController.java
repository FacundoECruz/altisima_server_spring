package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.jwtTest.AuthResponse;
import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserServiceAPI userServiceAPI;

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> findUser(@PathVariable String username) {
        ServiceResult<User> user = userServiceAPI.get(username);
        if (user.isSuccess()) {
            return new ResponseEntity<>(user.getData(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(user.getErrorMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        ServiceResult<List<User>> result = userServiceAPI.getAll();
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getData());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getErrorMessage());
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            AuthResponse token = userLogin(loginRequestDto);
            return ResponseEntity.ok(token);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    private AuthResponse userLogin(LoginRequestDto loginRequestDto) throws JsonProcessingException {
        LoginRequest loginRequest = loginRequestDto.toDomain();
        return userServiceAPI.login(loginRequest);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<?> putUser(@PathVariable String username, @RequestBody EditUserDto userChangesDto) {
        try {
            ServiceResult<User> user = userEdit(username, userChangesDto);
            if(user.isSuccess())
                return new Response().build(user);
            else
                return new ResponseEntity<>(user.getErrorMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
    private ServiceResult<User> userEdit(String username, EditUserDto userChangesDto) {
        EditUser userChanges = userChangesDto.toDomain(username);
        return userServiceAPI.put(userChanges);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        ServiceResult<User> user = userServiceAPI.get(username);
        if (user.isSuccess()) {
            userServiceAPI.delete(user.getData().getUsername());
            return new ResponseEntity<>("Exitosamente borrado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontro el usuario", HttpStatus.NOT_FOUND);
        }
    }
}

