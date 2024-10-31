package com.bxacosta.accountservice.services;

import com.bxacosta.accountservice.entities.Account;
import com.bxacosta.accountservice.entities.Transaction;
import com.bxacosta.accountservice.enums.OperationTypeEnum;
import com.bxacosta.accountservice.exeptions.DataValidationException;
import com.bxacosta.accountservice.repositories.JpaTransactionRepository;
import com.bxacosta.accountservice.utils.ErrorMessageConstants;
import com.bxacosta.accountservice.utils.TransactionUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountService accountService;
    private final JpaTransactionRepository jpaTransactionRepository;

    public Optional<Transaction> getById(Long id) {
        return jpaTransactionRepository.findById(id);
    }

    public List<Transaction> getAll() {
        return jpaTransactionRepository.findAll();
    }

    @Transactional
    public Transaction create(Transaction transaction, String accountNumber) {
        TransactionUtils.validate(OperationTypeEnum.CREATE, transaction, accountNumber);

        Account account = accountService.getByNumber(accountNumber)
                .orElseThrow(() -> new DataValidationException(String.format(ErrorMessageConstants.RESOURCE_NOT_FOUND_BY_FIELD, "Account", "accountNumber")));

        BigDecimal availableBalance = account.getBalance().add(transaction.getValue());

        if (availableBalance.compareTo(BigDecimal.ZERO) < 0) {
            log.info("Account balance: {}, is insufficient for transaction value: {}", account.getBalance(), transaction.getValue());
            throw new DataValidationException(ErrorMessageConstants.INSUFFICIENT_BALANCE);
        }

        transaction.setId(null);
        transaction.setAccountId(account.getId());
        transaction.setInitialBalance(account.getBalance());
        transaction.setAvailableBalance(availableBalance);
        transaction.setCreatedAt(ZonedDateTime.now());

        account.setBalance(availableBalance);
        account.setUpdatedAt(ZonedDateTime.now());

        return jpaTransactionRepository.saveAndFlush(transaction);
    }
}
