package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoTransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoTransactionStatusRepository extends JpaRepository<CryptoTransactionStatus, Long> {
    Optional<CryptoTransactionStatus> findByName(String name);
}
