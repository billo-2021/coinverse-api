package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByAccountId(final Long id);
    Optional<Wallet> findByAccountIdAndCurrencyId(Long accountId, Long currencyId);
    Page<Wallet> findByAccountId(final Long id, Pageable pageable);
}
