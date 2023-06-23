package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyPairRepository extends JpaRepository<CurrencyPair, Long> {
    Optional<CurrencyPair> findByName(String name);
}
