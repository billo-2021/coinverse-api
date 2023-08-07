package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    Optional<CryptoCurrency> findByCurrencyCodeIgnoreCase(String currencyCode);
}
