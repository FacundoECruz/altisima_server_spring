package com.facu.altisima.controller.dto.legacyDtos;

import java.util.Objects;

public class CreatePlayerWithTokenDto {
    private String username;
    private String token;
    private String createdBy;

    public CreatePlayerWithTokenDto() {

    }

    public CreatePlayerWithTokenDto(String username, String token, String createdBy) {
        this.username = username;
        this.token = token;
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreatePlayerWithTokenDto that = (CreatePlayerWithTokenDto) o;
        return Objects.equals(username, that.username) && Objects.equals(token, that.token) && Objects.equals(createdBy, that.createdBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token, createdBy);
    }
}
