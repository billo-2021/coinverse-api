package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.AccountToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountTokenRepository extends JpaRepository<AccountToken, Long> {
    Optional<AccountToken> findByAccountIdAndTypeId(final @NotNull Long accountId, final @NotNull Long typeId);
    List<AccountToken> findByAccountId(final @NotNull Long accountId);
}
