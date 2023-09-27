package com.facu.altisima.controller.dto;

import com.facu.altisima.model.User;

import java.util.Objects;

public class EditUser {
    private String username;
    private String password;
    private String image;

    public EditUser(String username, String password, String image) {
        this.username = username;
        this.password = password;
        this.image = image;
    }

    public EditUser() {

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
        EditUser editUser = (EditUser) o;
        return Objects.equals(username, editUser.username) && Objects.equals(password, editUser.password) && Objects.equals(image, editUser.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, image);
    }
}
