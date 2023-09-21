package com.facu.altisima.controller.dto.legacyDtos;

import java.util.Objects;

public class EditUserDto {
    private String password;
    private String image;

    public EditUserDto(String password, String image) {
        this.password = password;
        this.image = image;
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
        EditUserDto that = (EditUserDto) o;
        return Objects.equals(password, that.password) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, image);
    }
}
