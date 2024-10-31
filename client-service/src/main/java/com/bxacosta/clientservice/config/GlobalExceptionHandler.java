package com.bxacosta.clientservice.config;

import com.bxacosta.clientservice.dtos.ErrorResponseDTO;
import com.bxacosta.clientservice.exeptions.BaseRestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MissingRequestValueException.class, TypeMismatchException.class})
    public ResponseEntity<ErrorResponseDTO> handleBaseRestException(TypeMismatchException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(buildErrorResponseDTO(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BaseRestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBaseRestException(BaseRestException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(buildErrorResponseDTO(exception), exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(buildErrorResponseDTO(exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDTO buildErrorResponseDTO(Throwable exception) {
        return ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();
    }
}