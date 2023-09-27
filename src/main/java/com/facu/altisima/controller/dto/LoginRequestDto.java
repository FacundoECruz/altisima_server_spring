package com.facu.altisima.controller.dto;

import java.util.Objects;

import static com.facu.altisima.controller.dto.PasswordDto.EMPTY_FIELD_MSG;

public class LoginRequestDto {
    private String username;
    private String password;

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequestDto() {

    }

    public LoginRequest toDomain() {
        validate();
        return new LoginRequest(username, password);
    }

    private void validate() {
        if(username.equals("") || password.equals("")) {
            throw new RuntimeException(EMPTY_FIELD_MSG);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequestDto that = (LoginRequestDto) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
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
}

