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
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        User obj = userServiceAPI.save(user);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }
    @PostMapping(value = "/login")
    public ResponseEntity loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userServiceAPI.login(loginRequest);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario o contraseña invalidos");
        }
    }
    @GetMapping(value = "/{id}")
    public User findUser(@PathVariable String id) {
        return userServiceAPI.get(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> putUser(@PathVariable String id, @RequestBody User userChanges) {
        User user = userServiceAPI.put(id, userChanges);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        User user = userServiceAPI.get(id);
        if (user != null) {
            userServiceAPI.delete(id);
            return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}

