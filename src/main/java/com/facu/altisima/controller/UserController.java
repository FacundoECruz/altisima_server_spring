package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.controller.dto.LoginRequestDto;
import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
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
            User retrievedUser = user.getData();
            return new ResponseEntity<>(retrievedUser, HttpStatus.OK);
        } else {
            String errorMessage = user.getErrorMessage();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        ServiceResult<List<User>> result = userServiceAPI.getAll();

        if (result.isSuccess()) {
            List<User> users = result.getData();
            return ResponseEntity.ok(users);
        } else {
            String errorMessage = result.getErrorMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        ServiceResult<User> result = userServiceAPI.save(user);
        if (result.isSuccess()) {
            User retrievedUser = result.getData();
            return ResponseEntity.ok(retrievedUser);
        } else {
            String errorMessage = result.getErrorMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            ServiceResult<User> user = userLogin(loginRequestDto);
            return new Response().build(user);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private ServiceResult<User> userLogin(LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = loginRequestDto.toDomain();
        return userServiceAPI.login(loginRequest);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<?> putUser(@PathVariable String username, @RequestBody EditUserDto userChangesDto) {
        try {
            ServiceResult<User> user = userEdit(username, userChangesDto);
            return new Response().build(user);
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
        if (user.getErrorMessage() == null) {
            User userToDelete = user.getData();
            userServiceAPI.delete(userToDelete.getUsername());
            return new ResponseEntity<>("Exitosamente borrado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontro el usuario", HttpStatus.NOT_FOUND);
        }
    }
}

