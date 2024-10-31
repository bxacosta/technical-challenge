package com.bxacosta.accountservice.controllers;


import com.bxacosta.accountservice.dtos.ListResponseDTO;
import com.bxacosta.accountservice.dtos.TransactionRequestDTO;
import com.bxacosta.accountservice.dtos.TransactionResponseDTO;
import com.bxacosta.accountservice.entities.Transaction;
import com.bxacosta.accountservice.mappers.TransactionMapper;
import com.bxacosta.accountservice.services.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionMapper transactionMapper;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<ListResponseDTO<TransactionResponseDTO>> getAll() {
        List<TransactionResponseDTO> transactions = transactionService.getAll().stream()
                .map(transactionMapper::toResponseDTO)
                .toList();

        return ResponseEntity.ok(ListResponseDTO.from(transactions));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getById(@PathVariable Long id) {
        return transactionService.getById(id)
                .map(transactionMapper::toResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody TransactionRequestDTO transaction) {
        log.info("New Transaction received: {}", transaction);
        Transaction created = transactionService.create(transactionMapper.toEntity(transaction), transaction.getAccountNumber());
        return ResponseEntity.ok(transactionMapper.toResponseDTO(created));
    }
}