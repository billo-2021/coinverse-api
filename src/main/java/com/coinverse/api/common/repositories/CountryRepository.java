package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByCodeIgnoreCase(String code);
}
