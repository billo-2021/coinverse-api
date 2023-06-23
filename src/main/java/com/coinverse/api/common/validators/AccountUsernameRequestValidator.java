package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUsernameRequestValidator implements RequestValidator<String, Account> {
    private final AccountRepository accountRepository;

    @Override
    public Account validate(@NotNull String username) throws InvalidRequestException, MappingException {
        return accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new InvalidRequestException("Invalid username '" + username + "'")
                );
    }
}
