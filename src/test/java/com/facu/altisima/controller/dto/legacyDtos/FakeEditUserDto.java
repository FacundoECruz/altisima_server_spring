package com.facu.altisima.controller.dto.legacyDtos;

import java.util.function.Supplier;

public class FakeEditUserDto extends EditUserDto{
    Runnable internalValidate;

    public FakeEditUserDto(String password, String image, Runnable validateFunction) {
        super(password, image);
        this.internalValidate = validateFunction;
    }

    @Override
    protected void validate() throws RuntimeException{
        internalValidate.run();
    }
}
