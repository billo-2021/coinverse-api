package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoTransactionRepository extends JpaRepository<CryptoTransaction, Long> {
    Page<CryptoTransaction> findAllByAccountId(Long id, Pageable pageable);
}
