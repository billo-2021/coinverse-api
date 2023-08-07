package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoCurrency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, Long> {
    Optional<CryptoCurrency> findByCurrencyCodeIgnoreCase(String currencyCode);
    @Query(value = """
            SELECT cryptoCurrency
            FROM CryptoCurrency cryptoCurrency
            WHERE lower(cryptoCurrency.currency.code) LIKE CONCAT('%', lower(:query), '%')
            OR lower(cryptoCurrency.currency.name) LIKE CONCAT('%', lower(:query), '%')
            """)
    Page<CryptoCurrency> search(String query, Pageable pageable);
}
