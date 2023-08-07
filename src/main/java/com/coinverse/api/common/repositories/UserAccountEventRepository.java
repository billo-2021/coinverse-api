package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.UserAccountEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountEventRepository extends JpaRepository<UserAccountEvent, Long> {
    Page<UserAccountEvent> findByAccountId(Long accountId, Pageable pageable);
    Page<UserAccountEvent> findByAccountIdAndEventId(Long accountId, Long eventId, Pageable pageable);
    Page<UserAccountEvent> findByAccountIdAndEventTypeCodeIgnoreCase(Long accountId, String eventCode, Pageable pageable);
}
