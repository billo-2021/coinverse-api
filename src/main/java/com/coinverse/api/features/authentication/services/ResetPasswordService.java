package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountTokenRepository;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.features.authentication.models.ResetPasswordRequest;
import com.coinverse.api.features.authentication.models.TokenRequest;
import com.coinverse.api.features.authentication.models.TokenVerifyRequest;
import com.coinverse.api.features.authentication.validators.ResetPasswordValidator;
import com.coinverse.api.features.messaging.models.Message;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {
    private static final String TOKEN_TYPE_NAME = AccountTokenTypeEnum.RESET_PASSWORD_LINK_TOKEN.getName();

    private final AccountRepository accountRepository;
    private final AccountTokenRepository accountTokenRepository;

    private final AccountTokenService accountTokenService;
    private final ResetPasswordTokenGenerator resetPasswordTokenGenerator;
    private final ResetPasswordValidator resetPasswordValidator;

    public void requestResetPasswordToken(@NotNull final TokenRequest tokenRequest) {
        final Account account = resetPasswordValidator.validateRequestResetPasswordToken(tokenRequest);

        accountTokenService.requestToken(
                account.getId(),
                TOKEN_TYPE_NAME,
                tokenRequest,
                resetPasswordTokenGenerator,
                (token) -> Message
                        .builder()
                        .subject("Resent password link")
                        .content("Use link: " + token.getKey() + " to reset your password")
                        .build()
                );
    }

    @Transactional
    public void resetPassword(@NotNull final ResetPasswordRequest resetPasswordRequest) {
        final Account account = resetPasswordValidator.validateResetPassword(resetPasswordRequest);

        final TokenVerifyRequest tokenVerifyRequest = TokenVerifyRequest
                .builder()
                .username(resetPasswordRequest.getUsername())
                .token(resetPasswordRequest.getToken())
                .build();

        final AccountTokenResponse accountTokenResponse = accountTokenService.verifyToken(account.getId(),
                TOKEN_TYPE_NAME,
                tokenVerifyRequest
        );

        accountRepository.save(account);
        accountTokenRepository.deleteById(accountTokenResponse.getId());
    }
}
