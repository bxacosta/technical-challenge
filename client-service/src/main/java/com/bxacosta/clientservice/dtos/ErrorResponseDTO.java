package com.bxacosta.clientservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 9125691424859263508L;

    private String message;
    private LocalDateTime timestamp;
}
