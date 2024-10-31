package com.bxacosta.accountservice.repositories;

import com.bxacosta.accountservice.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String number);

    @Modifying
    @Query("update Account a set a.active = :active where a.id = :id")
    void updateActive(@Param("id") Long id, @Param("active") Boolean active);

    @Modifying
    @Query("update Account a set a.active = :active where a.clientId = :clientId")
    void updateActiveByClientId(@Param("clientId") Long clientId, @Param("active") Boolean active);
}
