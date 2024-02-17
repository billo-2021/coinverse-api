package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.validators.AccountRequestValidator;
import com.coinverse.api.features.administration.models.UpdateAccountEnabledRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountManager {
    private final AccountRepository accountRepository;

    private final AccountRequestValidator accountRequestValidator;

    @Transactional
    public void updateAccountEnabled(UpdateAccountEnabledRequest updateAccountEnabledRequest) {
        final Account account = accountRequestValidator.validateUsername(updateAccountEnabledRequest.getUsername());
        account.setIsEnabled(updateAccountEnabledRequest.getIsEnabled());
        accountRepository.save(account);
    }
}
