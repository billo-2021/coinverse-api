package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.models.AccountTokenTypeEnum;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountTokenRepository;
import com.coinverse.api.common.repositories.AccountTokenTypeRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.authentication.models.ResetPasswordRequest;
import com.coinverse.api.features.authentication.models.TokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPasswordValidator {
    private static final String TOKEN_TYPE_CODE = AccountTokenTypeEnum.RESET_PASSWORD_LINK_TOKEN.getCode();

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final AccountTokenTypeRepository accountTokenTypeRepository;

    public Account validateRequestResetPasswordToken(TokenRequest tokenRequest) {
        return validateAccountUsername(tokenRequest.getUsername());
    }

    public Account validateResetPassword(ResetPasswordRequest resetPasswordRequest) {
        Account account = validateAccountUsername(resetPasswordRequest.getUsername());
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));

        return account;
    }

    private Account validateAccountUsername(String username) {
        return accountRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);
    }

    public AccountToken validateTokenKey(String key) {
        final AccountTokenType accountTokenType = accountTokenTypeRepository
                .findByCodeIgnoreCase(TOKEN_TYPE_CODE)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);

        return accountTokenRepository
                .findByKeyAndTypeId(key, accountTokenType.getId())
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);
    }
}
