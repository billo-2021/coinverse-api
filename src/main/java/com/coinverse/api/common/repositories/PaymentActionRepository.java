package com.coinverse.api.common.repositories;

import com.coinverse.api.common.entities.PaymentAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentActionRepository extends JpaRepository<PaymentAction, Long> {
    Optional<PaymentAction> findByCodeIgnoreCase(String code);
}
