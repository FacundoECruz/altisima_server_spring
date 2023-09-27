package com.facu.altisima.controller.dto.legacyDtos;

import com.facu.altisima.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserDtoTest {
    public static final String VALID_USERNAME = "username";
    public static final String VALID_EMAIL = "user@user.com";
    public static final String VALID_IMAGE_URL = "www.image.com";
    public static final String VALID_PASSWORD = "akshdfjasf";

    @Test
    public void invalidEmail() {
        String invalidEmail = "aguanteMessi";
        CreateUserDto subject = new CreateUserDto(
                VALID_USERNAME,
                invalidEmail,
                VALID_PASSWORD,
                VALID_IMAGE_URL);

        Assertions.assertThrows(RuntimeException.class, () -> subject.toDomain(), CreateUserDto.invalidEmailMsg(invalidEmail));
    }

    @Test
    public void emptyField() {
        CreateUserDto emptyUsername = new CreateUserDto(
                "",
                VALID_EMAIL,
                VALID_PASSWORD,
                VALID_IMAGE_URL);
        Assertions.assertThrows(RuntimeException.class, () -> emptyUsername.toDomain(), CreateUserDto.EMPTY_FIELD_MSG);

        CreateUserDto emptyEmail = new CreateUserDto(VALID_USERNAME, "", VALID_PASSWORD, VALID_IMAGE_URL);
        Assertions.assertThrows(RuntimeException.class, () -> emptyEmail.toDomain(), CreateUserDto.EMPTY_FIELD_MSG);

        CreateUserDto emptyPassword = new CreateUserDto(VALID_USERNAME, VALID_EMAIL, "", VALID_IMAGE_URL);
        Assertions.assertThrows(RuntimeException.class, () -> emptyPassword.toDomain(), CreateUserDto.EMPTY_FIELD_MSG);
    }

    @Test
    public void defaultImageUrl() {
        CreateUserDto emptyImage = new CreateUserDto(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD,"");
        User userEmptyImage = emptyImage.toDomain();
        assertEquals(userEmptyImage.getImage(), CreateUserDto.DEFAULT_IMAGE_URL);

        CreateUserDto withImage = new CreateUserDto(VALID_USERNAME, VALID_EMAIL, VALID_PASSWORD, VALID_IMAGE_URL);
        User userWithImage = withImage.toDomain();
        assertEquals(userWithImage.getImage(), VALID_IMAGE_URL);
    }
}