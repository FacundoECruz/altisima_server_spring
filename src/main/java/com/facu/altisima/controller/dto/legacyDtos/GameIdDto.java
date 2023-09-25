package com.facu.altisima.controller.dto.legacyDtos;

import java.util.Objects;

public class GameIdDto {
    String id;

    public GameIdDto(String id) {
        this.id = id;
    }

    public GameIdDto() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameIdDto gameIdDto = (GameIdDto) o;
        return Objects.equals(id, gameIdDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
