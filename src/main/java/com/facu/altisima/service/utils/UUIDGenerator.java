package com.facu.altisima.service.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator implements IdGenerator{

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
