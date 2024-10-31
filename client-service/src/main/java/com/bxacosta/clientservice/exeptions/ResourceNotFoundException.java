package com.bxacosta.clientservice.exeptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class ResourceNotFoundException extends BaseRestException {

    @Serial
    private static final long serialVersionUID = 4275281014386964317L;

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
