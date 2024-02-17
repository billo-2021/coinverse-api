package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.Currency;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCodeIgnoreCase(String code);
    List<Currency> findByTypeNameIgnoreCase(String name);
    Page<Currency> findByTypeNameIgnoreCase(String typeName, Pageable page);
}
