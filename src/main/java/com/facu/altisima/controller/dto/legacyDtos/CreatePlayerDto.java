package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.model.Player;
import com.facu.altisima.service.utils.IdGenerator;
import com.facu.altisima.service.utils.UUIDGenerator;

import java.util.Objects;

public class CreatePlayerDto {
    private String username;
    private String createdBy;
    private Long createdDate;

    IdGenerator idGenerator = new UUIDGenerator();
    String defaultPlayerImageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwUmQOGfv3HMATaiRo8hDCdcu23Otwqg2pEg&usqp=CAU";

    public Player toPlayer(){
        return new Player(idGenerator.generate(), username, defaultPlayerImageUrl, 0,0,0, createdBy, createdDate);
    }

    public CreatePlayerDto() {

    }

    public CreatePlayerDto(String username, String createdBy, Long createdDate) {
        this.username = username;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
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
        return Objects.equals(username, that.username) && Objects.equals(createdBy, that.createdBy) && Objects.equals(createdDate, that.createdDate);
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, createdBy, createdDate);
    }
}
