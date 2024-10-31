package com.bxacosta.accountservice.repositories;

import com.bxacosta.accountservice.dtos.AccountTransactionReportDTO;
import com.bxacosta.accountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface JpaAccountTransactionRepository extends JpaRepository<Account, Long> {

    @Query(""" 
            SELECT new com.bxacosta.accountservice.dtos.AccountTransactionReportDTO(
                a.number,
                a.type,
                a.active,
                t.value,
                t.initialBalance,
                t.availableBalance,
                t.createdAt)
            FROM Account a
            JOIN Transaction t ON a.id = t.accountId
            WHERE a.clientId = :clientId AND t.createdAt BETWEEN :start AND :end""")
    List<AccountTransactionReportDTO> findAccountTransactionsByClientIdAndDateRange(
            @Param("clientId") Long clientId,
            @Param("start") ZonedDateTime start,
            @Param("end") ZonedDateTime end);
}
