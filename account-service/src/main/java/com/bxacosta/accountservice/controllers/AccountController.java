package com.bxacosta.accountservice.controllers;

import com.bxacosta.accountservice.dtos.AccountRequestDTO;
import com.bxacosta.accountservice.dtos.AccountResponseDTO;
import com.bxacosta.accountservice.dtos.ListResponseDTO;
import com.bxacosta.accountservice.entities.Account;
import com.bxacosta.accountservice.mappers.AccountMapper;
import com.bxacosta.accountservice.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountMapper accountMapper;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ListResponseDTO<AccountResponseDTO>> getAll() {
        List<AccountResponseDTO> accounts = accountService.getAll().stream()
                .map(accountMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(ListResponseDTO.from(accounts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> getById(@PathVariable Long id) {
        return accountService.getById(id)
                .map(accountMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AccountResponseDTO> create(@RequestBody AccountRequestDTO account) {
        Account created = accountService.create(accountMapper.toEntity(account));
        return ResponseEntity.ok(accountMapper.toResponseDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> update(@PathVariable Long id, @RequestBody AccountRequestDTO account) {
        Account updatedAccount = accountService.update(id, accountMapper.toEntity(account));
        return ResponseEntity.ok(accountMapper.toResponseDTO(updatedAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
