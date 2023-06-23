package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.AccountTokenType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountTokenTypeRepository extends JpaRepository<AccountTokenType, Long> {
    Optional<AccountTokenType> findByName(final @NotNull String name);
}
