package com.facu.altisima.service.utils;

import org.springframework.stereotype.Component;
import org.bson.types.ObjectId;

import java.util.UUID;

@Component
public class UUIDGenerator implements IdGenerator{

    @Override
    public ObjectId generate() {
        return new ObjectId();
    }
}
