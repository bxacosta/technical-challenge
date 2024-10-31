package com.bxacosta.accountservice.services;

import com.bxacosta.accountservice.entities.Account;
import com.bxacosta.accountservice.enums.OperationTypeEnum;
import com.bxacosta.accountservice.exeptions.DataValidationException;
import com.bxacosta.accountservice.exeptions.ResourceNotFoundException;
import com.bxacosta.accountservice.repositories.JpaAccountRepository;
import com.bxacosta.accountservice.utils.AccountUtils;
import com.bxacosta.accountservice.utils.ErrorMessageConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final JpaAccountRepository jpaAccountRepository;

    public Optional<Account> getById(Long id) {
        return jpaAccountRepository.findById(id);
    }

    public Optional<Account> getByNumber(String number) {
        return jpaAccountRepository.findByNumber(number);
    }

    public List<Account> getAll() {
        return jpaAccountRepository.findAll();
    }

    public Account create(Account account) {
        AccountUtils.validate(OperationTypeEnum.CREATE, account);

        this.validateExistingAccountByNumber(account.getNumber());

        if (ObjectUtils.isEmpty(account.getBalance())) account.setBalance(BigDecimal.ZERO);

        account.setId(null);
        account.setUpdatedAt(ZonedDateTime.now());
        account.setCreatedAt(ZonedDateTime.now());
        return jpaAccountRepository.save(account);
    }

    public Account update(Long id, Account account) {
        AccountUtils.validate(OperationTypeEnum.UPDATE, account);

        Account savedAccount = jpaAccountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessageConstants.RESOURCE_NOT_FOUND, "Account")));

        if (!savedAccount.getNumber().equals(account.getNumber()))
            this.validateExistingAccountByNumber(account.getNumber());

        account.setId(savedAccount.getId());
        account.setUpdatedAt(ZonedDateTime.now());
        account.setCreatedAt(savedAccount.getCreatedAt());
        return jpaAccountRepository.save(account);
    }

    @Transactional
    public void deactivate(Long id) {
        jpaAccountRepository.updateActive(id, false);
    }

    @Transactional
    public void deactivateByClientId(Long clientId) {
        jpaAccountRepository.updateActiveByClientId(clientId, false);
    }

    private void validateExistingAccountByNumber(String number) {
        jpaAccountRepository.findByNumber(number).ifPresent(existingAccount -> {
            throw new DataValidationException(String.format(ErrorMessageConstants.NON_UNIQUE_VALUE, "number"));
        });
    }
}
