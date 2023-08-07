package com.coinverse.api.features.balance.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletValidator {
    private final AccountRepository accountRepository;

    public Account validateWalletUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new MappingException("Unable to find account for username '"
                        + username + "'"));
    }
}
