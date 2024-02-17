package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUsernameRequestValidator implements RequestValidator<String, Account> {
    private final AccountRepository accountRepository;

    @Override
    public Account validate(String username) throws InvalidRequestException, MappingException {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        ErrorMessageUtils.getInvalidRequestException("username", username)
                );
    }
}
