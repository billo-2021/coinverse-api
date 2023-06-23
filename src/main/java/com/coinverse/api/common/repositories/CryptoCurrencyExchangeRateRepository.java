package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoCurrencyExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyExchangeRateRepository extends JpaRepository<CryptoCurrencyExchangeRate, Long> {
    Page<CryptoCurrencyExchangeRate> findByCurrencyPairId(Long currencyPairId, Pageable pageable);
    Optional<CryptoCurrencyExchangeRate> findFirstByCurrencyPairId(Long currencyPairId);
}
