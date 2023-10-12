package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.PasswordDto;

import java.util.Objects;

public class EditUserDto {
    private PasswordDto password;
    private String image;

    public EditUserDto(String password, String image) {
        this.password = new PasswordDto(password);
        this.image = image;
    }

    protected void validate() throws RuntimeException {
        password.validate();
    }
    public EditUser toDomain(String username) throws RuntimeException{
        validate();
        return new EditUser(username, password.getValue(), image);
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
        EditUserDto that = (EditUserDto) o;
        return Objects.equals(password, that.password)
                && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, image);
    }
}
