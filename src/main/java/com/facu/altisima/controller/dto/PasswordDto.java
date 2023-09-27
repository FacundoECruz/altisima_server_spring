package com.facu.altisima.controller.dto;

public class PasswordDto {
    public static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 12;
    public static final String PASSWORD_TOO_LONG_MSG = "La contraseña debe tener menos de " + MAX_LENGTH + " caracteres";
    private String value;

    public static final String PASSWORD_TOO_SHORT_MSG = "La contraseña debe tener mas de " + MIN_LENGTH + " caracteres";
    public static final String EMPTY_FIELD_MSG = "Completar todos los campos";
    public PasswordDto(String value) {
        this.value = value;
    }

    public void validate() throws RuntimeException {
        if (value.length() == 0)
            throw new RuntimeException(EMPTY_FIELD_MSG);
        else if(value.length() >= MAX_LENGTH)
            throw new RuntimeException(PASSWORD_TOO_LONG_MSG);
        else if(value.length() <= MIN_LENGTH){
            throw new RuntimeException(PASSWORD_TOO_SHORT_MSG);
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
