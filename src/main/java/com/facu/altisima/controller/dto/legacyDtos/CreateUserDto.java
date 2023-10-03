package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.controller.dto.PasswordDto;
import com.facu.altisima.model.User;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.UUIDGenerator;

import java.util.Objects;

public class CreateUserDto {
    public static final String EMPTY_FIELD_MSG = "Completar todos los campos";
    public static final String DEFAULT_IMAGE_URL = "https://play-lh.googleusercontent.com/rX_nOuUDijsV_NnWZP9JgYTsFpxn5y7qCqDxFIpZ-BqiJu8un7UbdSgVTZSrJuzAlQ";

    IdGenerator idGenerator = new UUIDGenerator();
    private String username;
    private String email;
    private PasswordDto password;
    private String image;

    public CreateUserDto(String username, String email, String password, String image) {
        this.username = username;
        this.email = email;
        this.password = new PasswordDto(password);
        this.image = image;
    }

    public void validate() {
        password.validate();
        if (username.equals("") || email.equals("")) {
            throw new RuntimeException(EMPTY_FIELD_MSG);
        } else if (!email.contains("@")) {
            throw new RuntimeException(invalidEmailMsg(email));
        }
    }

    public static String invalidEmailMsg(String email) {
        return "El email no es valido: " + email;
    }

    public User toDomain() {
        validate();
        return new User(idGenerator.generate(), username, email, getDefaultImage(), password.getValue(), 0);
    }

    private String getDefaultImage() {
        return image.equals("") ? DEFAULT_IMAGE_URL : image;

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
        return password.getValue();
    }

    public void setPassword(String password) {
        this.password = new PasswordDto(password);
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
