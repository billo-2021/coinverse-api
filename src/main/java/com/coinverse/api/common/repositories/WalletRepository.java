package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    List<Wallet> findByAccountId(final Long id);
    Page<Wallet> findByAccountId(final Long id, Pageable pageable);
}