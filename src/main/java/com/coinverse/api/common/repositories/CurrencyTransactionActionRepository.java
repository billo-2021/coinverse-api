package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyTransactionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyTransactionActionRepository extends JpaRepository<CurrencyTransactionAction, Long> {
    Optional<CurrencyTransactionAction> findByCodeIgnoreCase(String code);
}
