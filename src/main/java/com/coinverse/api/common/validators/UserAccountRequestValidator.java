package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAccountRequestValidator {
    private final AccountRepository accountRepository;
    public Account validateUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);
    }
}
