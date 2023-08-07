package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByTypeId(Long typeId, Pageable pageable);
    Page<Event> findByTypeCodeIgnoreCase(String code, Pageable pageable);
}
