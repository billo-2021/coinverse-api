package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CryptoCurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoCurrencyPairRepository extends JpaRepository<CryptoCurrencyPair, Long> {
    Optional<CryptoCurrencyPair> findByName(String name);

    @Query("""
            SELECT currencyPair
            FROM CryptoCurrencyPair currencyPair
            WHERE (currencyPair.baseCurrency.code = :firstCode
            AND currencyPair.quoteCurrency.code = :secondCode)
            OR (currencyPair.baseCurrency.code = :secondCode
            AND currencyPair.quoteCurrency.code = :firstCode)""")
    Optional<CryptoCurrencyPair> findByCurrencyCodeCombination(@Param("firstCode") String firstCurrencyCode,
                                                               @Param("secondCode") String secondCurrencyCode);

    @Query("""
            SELECT currencyPair
            FROM CryptoCurrencyPair currencyPair
            WHERE (currencyPair.baseCurrency.id = :firstId
            AND currencyPair.quoteCurrency.id = :secondId)
            OR (currencyPair.baseCurrency.id = :secondId
            AND currencyPair.quoteCurrency.id = :firstId)
            """)
    Optional<CryptoCurrencyPair> findByCurrencyIdCombination(@Param("firstId") Long firstCurrencyId,
                                                               @Param("secondId") Long secondCurrencyId);
}
