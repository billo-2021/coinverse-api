package com.coinverse.api.features.transfer.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.repositories.AccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentValidator {
    private final AccountRepository accountRepository;

    public Account validatePaymentUsername(@NotNull final String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(InvalidRequestException::new);
    }
}
