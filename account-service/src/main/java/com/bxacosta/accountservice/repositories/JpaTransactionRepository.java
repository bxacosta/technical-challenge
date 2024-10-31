package com.bxacosta.accountservice.repositories;

import com.bxacosta.accountservice.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTransactionRepository extends JpaRepository<Transaction, Long> {
}
