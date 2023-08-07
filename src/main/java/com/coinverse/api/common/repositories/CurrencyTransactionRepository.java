package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyTransactionRepository extends JpaRepository<CurrencyTransaction, Long> {
    Page<CurrencyTransaction> findAllByAccountId(Long id, Pageable pageable);
}
