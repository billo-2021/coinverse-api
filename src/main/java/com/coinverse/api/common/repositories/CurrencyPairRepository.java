package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyPair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {
    Optional<CurrencyPair> findByNameIgnoreCase(String name);
    Page<CurrencyPair> findByTypeId(Long typeId, Pageable pageable);
    List<CurrencyPair> findByTypeNameIgnoreCase(String name);
    Page<CurrencyPair> findByTypeNameIgnoreCase(String name, Pageable pageable);
    @Query("""
            SELECT currencyPair
            FROM CurrencyPair currencyPair
            WHERE (currencyPair.baseCurrency.code = :firstCode
            AND currencyPair.quoteCurrency.code = :secondCode)
            OR (currencyPair.baseCurrency.code = :secondCode
            AND currencyPair.quoteCurrency.code = :firstCode)""")
    Optional<CurrencyPair> findByCurrencyCodeCombination(@Param("firstCode") String firstCurrencyCode,
                                                         @Param("secondCode") String secondCurrencyCode);

    @Query("""
            SELECT currencyPair
            FROM CurrencyPair currencyPair
            WHERE (currencyPair.baseCurrency.id = :firstId
            AND currencyPair.quoteCurrency.id = :secondId)
            OR (currencyPair.baseCurrency.id = :secondId
            AND currencyPair.quoteCurrency.id = :firstId)
            """)
    Optional<CurrencyPair> findByCurrencyIdCombination(@Param("firstId") Long firstCurrencyId,
                                                       @Param("secondId") Long secondCurrencyId);
}
