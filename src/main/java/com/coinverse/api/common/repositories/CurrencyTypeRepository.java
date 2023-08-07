package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyTypeRepository extends JpaRepository<CurrencyType, Long> {
    Optional<CurrencyType> findByCodeIgnoreCase(String code);
}
