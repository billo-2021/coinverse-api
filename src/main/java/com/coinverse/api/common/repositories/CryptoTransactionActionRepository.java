package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoTransactionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoTransactionActionRepository extends JpaRepository<CryptoTransactionAction, Long> {
    Optional<CryptoTransactionAction> findByName(String name);
}
