package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.LoginRequest;
import com.facu.altisima.model.User;
import com.facu.altisima.service.api.UserServiceAPI;
import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceAPI userServiceAPI;

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
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        ServiceResult<User> user = userServiceAPI.login(loginRequest);
        if (user.isSuccess()) {
            User retrievedUser = user.getData();
            return ResponseEntity.ok(retrievedUser);
        } else {
            String errorMessage = user.getErrorMessage();
            if(errorMessage == "No se encontraron usuarios") {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
            }
        }
    }
    @GetMapping(value = "/{username}")
    public ResponseEntity<?> findUser(@PathVariable String username) {
        ServiceResult<User> user = userServiceAPI.get(username);
        if (user.isSuccess()) {
            User retrievedUser = user.getData();
            return ResponseEntity.ok(retrievedUser);
        } else {
            String errorMessage = user.getErrorMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<User> putUser(@PathVariable String username, @RequestBody User userChanges) {
        ServiceResult<User> user = userServiceAPI.put(username, userChanges);
        return new ResponseEntity<User>(user.getData(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        ServiceResult<User> user = userServiceAPI.get(username);
        if (user.getErrorMessage() == null) {
            User userToDelete = user.getData();
            userServiceAPI.delete(userToDelete.getId());
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}

