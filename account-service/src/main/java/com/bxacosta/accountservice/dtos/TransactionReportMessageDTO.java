package com.bxacosta.accountservice.dtos;

import java.time.ZonedDateTime;

public record TransactionReportMessageDTO(Long clientId, ZonedDateTime start, ZonedDateTime end) {
}
