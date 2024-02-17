package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAddressIgnoreCase(String emailAddress);
    Optional<User> findByAccountId(Long accountId);

    Page<User> findByAccountIdNot(Long accountId, Pageable pageable);
}
