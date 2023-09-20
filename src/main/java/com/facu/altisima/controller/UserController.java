package com.facu.altisima.controller;

import com.facu.altisima.controller.dto.LoginRequestDto;
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
public class UserController {
    @Autowired
    private UserServiceAPI userServiceAPI;

    @GetMapping(value = "/{username}")
    public ResponseEntity<?> findUser(@PathVariable String username) {
        ServiceResult<User> user = userServiceAPI.get(username);
        if (user.isSuccess()) {
            User retrievedUser = user.getData();
            return ResponseEntity.ok(retrievedUser);
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
        ServiceResult<User> user = userServiceAPI.login(loginRequestDto);
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

    @PutMapping(value = "/{username}")
    public ResponseEntity<?> putUser(@PathVariable String username, @RequestBody User userChanges) {
        ServiceResult<User> user = userServiceAPI.put(username, userChanges);
        if(user.getErrorMessage() == null) {
        return new ResponseEntity<>(user.getData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        ServiceResult<User> user = userServiceAPI.get(username);
        if (user.getErrorMessage() == null) {
            User userToDelete = user.getData();
            userServiceAPI.delete(userToDelete.getId());
            return new ResponseEntity<>("Exitosamente borrado", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontro el usuario", HttpStatus.NOT_FOUND);
        }
    }
}

