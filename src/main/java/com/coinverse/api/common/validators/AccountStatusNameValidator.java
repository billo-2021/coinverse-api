package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountStatusRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountStatusNameValidator implements RequestValidator<String, AccountStatus> {
    private final AccountStatusRepository accountStatusRepository;

    @Override
    public AccountStatus validate(@NotNull String statusName) throws InvalidRequestException, MappingException {
        return accountStatusRepository
                .findByName(statusName)
                .orElseThrow(() ->
                        new MappingException("Invalid status '" + statusName + "'")
                );
    }
}
