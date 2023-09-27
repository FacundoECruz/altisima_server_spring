package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.controller.dto.EditUser;
import com.facu.altisima.controller.dto.PasswordDto;
import org.junit.internal.runners.statements.RunAfters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import static org.junit.jupiter.api.Assertions.*;

class EditUserDtoTest {

    EditUser succeedEditUser = new EditUser("username", "asdfasdf", "www.image.com");

    @Test
    public void successfulToDomain() {
        EditUserDto editUserDto = new EditUserDto("asdfasdf", "www.image.com");
        String username = "username";

        Assertions.assertDoesNotThrow(() -> editUserDto.toDomain(username));
    }

    @Test
    public void invalid() {
        Runnable invalidThrow = () -> {throw new RuntimeException("invalid");};
        FakeEditUserDto fakeEditUserDto = new FakeEditUserDto("asdfasdf", "www.image.com", invalidThrow);
        Assertions.assertThrows(RuntimeException.class,() -> fakeEditUserDto.toDomain("username"));
    }
}