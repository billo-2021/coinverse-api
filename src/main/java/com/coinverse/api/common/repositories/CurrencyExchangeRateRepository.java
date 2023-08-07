package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {
    Page<CurrencyExchangeRate> findByCurrencyPairId(Long currencyPairId, Pageable pageable);
    Optional<CurrencyExchangeRate> findFirstByCurrencyPairId(Long currencyPairId);
}
