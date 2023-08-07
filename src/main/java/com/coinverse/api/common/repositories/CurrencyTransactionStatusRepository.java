package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyTransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyTransactionStatusRepository extends JpaRepository<CurrencyTransactionStatus, Long> {
    Optional<CurrencyTransactionStatus> findByCodeIgnoreCase(String code);
}
