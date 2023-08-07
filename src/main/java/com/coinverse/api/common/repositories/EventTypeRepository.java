package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventTypeRepository extends JpaRepository<EventType, Long> {
    Optional<EventType> findByCodeIgnoreCase(String code);
}
