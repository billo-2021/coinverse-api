package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.features.administration.validators.UserAccountValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountManager {
    private final AccountRepository accountRepository;
    private final UserAccountValidator userAccountValidator;

    @Transactional
    public void disableAccount(String username) {
        final Account account = userAccountValidator.validateUsername(username);
        account.setIsEnabled(false);
        accountRepository.save(account);
    }

    @Transactional
    public void enableUserAccount(@NotNull final String username) {
        final Account account = userAccountValidator.validateUsername(username);
        account.setIsEnabled(true);
        accountRepository.save(account);
    }
}
