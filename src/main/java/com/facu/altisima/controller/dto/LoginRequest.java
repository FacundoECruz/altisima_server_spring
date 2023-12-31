package com.facu.altisima.controller.dto;

import com.facu.altisima.model.User;

import java.util.Objects;

public class LoginRequest {
    public static final String UNAUTHORIZED_MSG = "Usuario o contraseña inválidos";
    private String username;
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequest() {

    }

    public void validate(User user){
        if(!user.getUsername().equals(username) || !user.getPassword().equals(password)){
            throw new RuntimeException(UNAUTHORIZED_MSG);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
