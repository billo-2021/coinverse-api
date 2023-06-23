package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.features.authentication.models.ResetPasswordRequest;
import com.coinverse.api.features.authentication.models.TokenRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPasswordValidator {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    public Account validateRequestResetPasswordToken(@NotNull final TokenRequest tokenRequest) {
        return validateAccountUsername(tokenRequest.getUsername());
    }

    public Account validateResetPassword(@NotNull final ResetPasswordRequest resetPasswordRequest) {
        Account account = validateAccountUsername(resetPasswordRequest.getUsername());
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));

        return account;
    }

    private Account validateAccountUsername(@NotNull final String username) {
        return accountRepository
                .findByUsername(username)
                .orElseThrow(InvalidRequestException::new);
    }
}
