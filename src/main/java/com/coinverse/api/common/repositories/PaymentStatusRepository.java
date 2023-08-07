package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentStatusRepository extends JpaRepository<PaymentStatus, Long> {
    Optional<PaymentStatus> findByCodeIgnoreCase(String code);
}
