package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddress(final @NotNull String emailAddress);
    Optional<User> findByAccountId(final @NotNull Long accountId);
}
