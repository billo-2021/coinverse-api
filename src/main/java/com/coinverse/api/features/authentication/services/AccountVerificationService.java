package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.models.AccountTokenTypeEnum;
import com.coinverse.api.common.models.AccountTokenResponse;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.features.authentication.models.TokenRequest;
import com.coinverse.api.features.authentication.models.TokenVerifyRequest;
import com.coinverse.api.features.authentication.validators.AccountVerificationValidator;
import com.coinverse.api.features.messaging.models.Message;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {
    private static final String TOKEN_TYPE_NAME = AccountTokenTypeEnum.VERIFICATION_TOKEN.getName();

    private final AccountService accountService;
    private final AccountTokenService accountTokenService;
    private final VerificationTokenGenerator verificationTokenGenerator;
    private final AccountVerificationValidator accountVerificationValidator;

    public void requestToken(@NotNull final TokenRequest tokenRequest) {
        final AccountResponse accountResponse = accountVerificationValidator.validateRequestToken(tokenRequest);

        accountTokenService.requestToken(accountResponse.getId(),
                TOKEN_TYPE_NAME,
                tokenRequest,
                verificationTokenGenerator,
                (token) -> Message
                        .builder()
                        .subject("Resent password link")
                        .content("Enter OTP: " + token.getKey() + " to verify your account")
                        .build()
        );
    }

    public void verifyAccount(@NotNull final TokenVerifyRequest verifyAccountRequest) {
        final AccountResponse accountResponse = accountVerificationValidator.validateVerifyAccount(verifyAccountRequest);

        final AccountTokenResponse accountTokenResponse = accountTokenService.verifyToken(accountResponse.getId(),
                TOKEN_TYPE_NAME,
                verifyAccountRequest
        );

        final Long accountId = accountTokenResponse.getAccount().getId();
        final Long accountVerificationId = accountTokenResponse.getId();

        accountService.verifyAccount(accountId, accountVerificationId);
    }
}
