package com.facu.altisima.controller;

import com.facu.altisima.service.utils.ServiceResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Response {
    public Response() {

    }

    public <A> ResponseEntity<?> build(ServiceResult<A> result) {
        if (result.isSuccess())
            return new ResponseEntity<>(result.getData(), HttpStatus.OK);
        else
            return new ResponseEntity<>(result.getErrorMessage(), HttpStatus.CONFLICT);
    }
}
