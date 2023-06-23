package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageStatusRepository extends JpaRepository<MessageStatus, Long> {
}
