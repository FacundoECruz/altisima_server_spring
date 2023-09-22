package com.facu.altisima.controller.dto.legacyDtos;

import java.util.Objects;

public class CreatePlayerDto {
    private String username;

    public CreatePlayerDto() {

    }

    public CreatePlayerDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatePlayerDto that = (CreatePlayerDto) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
