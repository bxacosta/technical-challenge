package com.bxacosta.accountservice.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3319833117098031994L;

    private String message;
    private LocalDateTime timestamp;
}
