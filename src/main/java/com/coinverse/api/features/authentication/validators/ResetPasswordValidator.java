package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.AccountTokenTypeEnum;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountTokenRepository;
import com.coinverse.api.common.repositories.AccountTokenTypeRepository;
import com.coinverse.api.common.repositories.UserRepository;
import com.coinverse.api.features.authentication.models.ResetPasswordRequest;
import com.coinverse.api.features.authentication.models.TokenRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResetPasswordValidator {
    private static final String TOKEN_TYPE_CODE = AccountTokenTypeEnum.RESET_PASSWORD_LINK_TOKEN.getCode();

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final AccountTokenTypeRepository accountTokenTypeRepository;

    public Account validateRequestResetPasswordToken(@NotNull final TokenRequest tokenRequest) {
        return validateAccountUsername(tokenRequest.getUsername());
    }

    public Account validateResetPassword(@NotNull final ResetPasswordRequest resetPasswordRequest) {
        Account account = validateAccountUsername(resetPasswordRequest.getUsername());
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));

        return account;
    }

    private Account validateAccountUsername(@NotNull final String username) {
        return accountRepository
                .findByUsernameIgnoreCase(username)
                .orElseThrow(InvalidRequestException::new);
    }

    public AccountToken validateTokenKey(final String key) {
        final AccountTokenType accountTokenType = accountTokenTypeRepository
                .findByCodeIgnoreCase(TOKEN_TYPE_CODE)
                .orElseThrow(InvalidRequestException::new);

        return accountTokenRepository
                .findByKeyAndTypeId(key, accountTokenType.getId())
                .orElseThrow(InvalidRequestException::new);
    }
}
