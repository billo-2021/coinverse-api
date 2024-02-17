package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountStatusRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountStatusNameValidator implements RequestValidator<String, AccountStatus> {
    private final AccountStatusRepository accountStatusRepository;

    @Override
    public AccountStatus validate(String statusName) throws InvalidRequestException, MappingException {
        return accountStatusRepository
                .findByCodeIgnoreCase(statusName)
                .orElseThrow(() ->
                        ErrorMessageUtils.getMappingException("status", statusName)
                );
    }
}
