package com.coinverse.api.features.balance.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletValidator {
    private final AccountRepository accountRepository;

    public Account validateWalletUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("account", "username", username));
    }
}
