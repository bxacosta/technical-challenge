package com.bxacosta.accountservice.exeptions;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class DataValidationException extends BaseRestException {

    @Serial
    private static final long serialVersionUID = 4643189714387659906L;

    public DataValidationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
