package com.bxacosta.clientservice.repositories;

import com.bxacosta.clientservice.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaClientRepository extends JpaRepository<Client, Long> {

    @Modifying
    @Query("update Client c set c.active = :active where c.id = :id")
    void updateActive(@Param(value = "id") Long id, @Param(value = "active") Boolean active);
}
