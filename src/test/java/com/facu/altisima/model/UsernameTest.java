package com.facu.altisima.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsernameTest {
    @Test
    public void createSuccessful() {
        String successfulUsername = "newUsername";
        Assertions.assertDoesNotThrow(() -> Username.of(successfulUsername));
    }

    @Test
    public void createUnsuccessful(){
        String unsuccessfulUsername = "newUsername#";
        String errMsg = "Cannot create Username with #: " + unsuccessfulUsername;

        Assertions.assertThrows(
                Exception.class,
                () -> Username.of(unsuccessfulUsername),
                errMsg
        );
    }
}