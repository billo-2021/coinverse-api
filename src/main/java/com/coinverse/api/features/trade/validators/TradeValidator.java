package com.coinverse.api.features.trade.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TradeValidator {
    private final AccountRepository accountRepository;

    public Account validateTradeUsername(String username) {
        return accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);
    }
}
