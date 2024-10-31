package com.bxacosta.clientservice.exeptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public class BaseRestException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -7372214901030674107L;

    private final HttpStatus status;

    public BaseRestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
