package com.bxacosta.accountservice.services;

import com.bxacosta.accountservice.dtos.AccountTransactionReportDTO;
import com.bxacosta.accountservice.repositories.JpaAccountTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTransactionService {

    private final JpaAccountTransactionRepository jpaAccountTransactionRepository;

    public List<AccountTransactionReportDTO> getAccountTransactions(
            Long clientId,
            ZonedDateTime start,
            ZonedDateTime end
    ) {
        return jpaAccountTransactionRepository.findAccountTransactionsByClientIdAndDateRange(clientId, start, end);
    }
}