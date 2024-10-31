package com.bxacosta.accountservice.exeptions;

import org.springframework.http.HttpStatus;

public class InsufficientBalanceException extends BaseRestException {
    public InsufficientBalanceException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
