package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.Message;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<Message> findByAccountId(Long accountId);
}
