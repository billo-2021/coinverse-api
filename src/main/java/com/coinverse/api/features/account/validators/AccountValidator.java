package com.coinverse.api.features.account.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.features.account.models.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountValidator {
    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;
    public Account validateChangePasswordRequest(String username, UpdatePasswordRequest updatePasswordRequest) {
        Account account = accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(InvalidRequestException::new);

        if (!passwordEncoder.matches(updatePasswordRequest.getCurrentPassword(), account.getPassword())) {
            throw new InvalidRequestException("Invalid current password provided");
        }

        return account;
    }
}
