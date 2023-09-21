package com.facu.altisima.controller.dto.legacyDtos;

import java.util.Objects;

public class CreateUserDto {
    private String username;
    private String email;
    private String password;
    private String image;

    public CreateUserDto(String username, String email, String password, String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateUserDto that = (CreateUserDto) o;
        return Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, image);
    }
}
