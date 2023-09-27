package com.facu.altisima.controller.dto;

import com.facu.altisima.controller.dto.legacyDtos.CreateUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PasswordDtoTest {

    @Test
    public void tooShort() {
        PasswordDto passwordDto = new PasswordDto("jaja");
        Exception exception = assertThrows(RuntimeException.class, () -> passwordDto.validate());
        assertEquals(PasswordDto.PASSWORD_TOO_SHORT_MSG, exception.getMessage());
    }

    @Test
    public void tooLong() {
        PasswordDto passwordDto = new PasswordDto("jajajajajajajajajajajajajajajajajajajaj");
        Exception exception = assertThrows(RuntimeException.class, () -> passwordDto.validate());
        assertEquals(PasswordDto.PASSWORD_TOO_LONG_MSG, exception.getMessage());
    }

    @Test
    public void success() {
        PasswordDto passwordDto = new PasswordDto("jajajaja");
        Assertions.assertDoesNotThrow(() -> passwordDto.validate());
    }

    @Test
    public void isEmpty() {
        PasswordDto passwordDto = new PasswordDto("");
        Exception exception = assertThrows(RuntimeException.class, () -> passwordDto.validate());
        assertEquals(PasswordDto.EMPTY_FIELD_MSG, exception.getMessage());
    }
}
