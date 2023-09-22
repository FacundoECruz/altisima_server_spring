package com.facu.altisima.model;

import com.facu.altisima.controller.dto.legacyDtos.EditUserDto;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class User {
    @Id
    private ObjectId _id;
    private String username;
    private String email;
    private String image;
    private String password;
    private Integer createdGames;

    public User() {

    }

    public Player toPlayer() {
        return new Player(this.getUsername(), this.getImage(), 0, 0, 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(image, user.image) && Objects.equals(password, user.password) && Objects.equals(createdGames, user.createdGames);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, image, password, createdGames);
    }

    public User(String username, String email, String image, String password, Integer createdGames) {
        this.username = username;
        this.email = email;
        this.image = image;
        this.password = password;
        this.createdGames = createdGames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCreatedGames() {
        return createdGames;
    }

    public void setCreatedGames(Integer createdGames) {
        this.createdGames = createdGames;
    }
}