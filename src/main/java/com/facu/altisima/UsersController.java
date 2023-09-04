package com.facu.altisima;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UsersController {
    @GetMapping("/users")
    public String allUsers(){
        return "All users route";
    }

    @PostMapping("/users")
    public String newUser(){
        return "New user created";
    }

    @PostMapping("/users/login")
    public String login() {
        return "Login route";
    }

    @GetMapping("/users/user")
    public String user(@RequestParam(name = "username") String username) {

        return "User: " + username;
    }

    @PatchMapping("/users/user")
    public String patchUser(@RequestParam(name = "username") String username) {

        return "Patch User: " + username;
    }

    @DeleteMapping("/users/user")
    public String deleteUser(@RequestParam(name = "username") String username) {

        return "Delete User: " + username;
    }
}
