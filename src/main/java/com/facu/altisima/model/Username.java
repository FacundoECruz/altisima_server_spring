package com.facu.altisima.model;

public class Username {
    private String value;

    private Username(String value){
        this.value = value;
    }

    static public Username of(String value) throws Exception {
        if(value.contains("#")){
            throw new Exception("Cannot create username");
        } else {
            return new Username(value);
        }
    }
}
