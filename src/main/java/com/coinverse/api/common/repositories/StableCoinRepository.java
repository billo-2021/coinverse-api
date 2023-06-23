package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.StableCoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StableCoinRepository extends JpaRepository<StableCoin, Long> {
    Optional<StableCoin> findByCurrencyCode(String currencyCode);
}
