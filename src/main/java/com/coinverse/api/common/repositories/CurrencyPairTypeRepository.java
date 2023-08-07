package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyPairType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyPairTypeRepository extends JpaRepository<CurrencyPairType, Long> {
    Optional<CurrencyPairType> findByCodeIgnoreCase(String code);
}
