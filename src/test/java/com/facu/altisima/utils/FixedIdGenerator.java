package com.facu.altisima.utils;

import com.facu.altisima.service.utils.IdGenerator;
import org.bson.types.ObjectId;

public class FixedIdGenerator implements IdGenerator {
    private String fixedId;

    public FixedIdGenerator(String fixedId){
        this.fixedId = fixedId;
    }

    @Override
    public ObjectId generate() {
        try {
            return new ObjectId(fixedId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
