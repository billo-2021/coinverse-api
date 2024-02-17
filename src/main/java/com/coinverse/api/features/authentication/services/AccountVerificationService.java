package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.models.AccountTokenTypeEnum;
import com.coinverse.api.common.models.AccountTokenResponse;
import com.coinverse.api.common.repositories.UserRepository;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.authentication.models.TokenRequest;
import com.coinverse.api.features.authentication.models.TokenVerifyRequest;
import com.coinverse.api.features.authentication.validators.AccountVerificationValidator;
import com.coinverse.api.features.messaging.models.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountVerificationService {
    private static final String TOKEN_TYPE_CODE = AccountTokenTypeEnum.VERIFICATION_TOKEN.getCode();

    private final AccountService accountService;
    private final AccountTokenService accountTokenService;
    private final VerificationTokenGenerator verificationTokenGenerator;
    private final AccountVerificationValidator accountVerificationValidator;
    private final UserRepository userRepository;

    public void requestToken(TokenRequest tokenRequest) {
        final AccountResponse accountResponse = accountVerificationValidator.validateRequestToken(tokenRequest);
        final User user = userRepository.findByAccountId(accountResponse.getId())
                .orElseThrow(() -> ErrorMessageUtils.getMappingException("account.status", accountResponse.getUsername()));

        accountTokenService.requestToken(accountResponse.getId(),
                TOKEN_TYPE_CODE,
                tokenRequest,
                verificationTokenGenerator,
                (token) -> Message
                        .builder()
                        .subject("Account Activation")
                        .content("Hi, " + user.getFirstName() + " " + user.getLastName() + "\n\n" +
                                "Enter OTP: "
                                + token.getKey() + "\nTo verify your account.\n\n"
                                + "\n\nKind regards, Coinverse Team.")
                        .build()
        );
    }

    public void verifyAccount(TokenVerifyRequest verifyAccountRequest) {
        final AccountResponse accountResponse = accountVerificationValidator.validateVerifyAccount(verifyAccountRequest);

        final AccountTokenResponse accountTokenResponse = accountTokenService.verifyToken(accountResponse.getId(),
                TOKEN_TYPE_CODE,
                verifyAccountRequest
        );

        final Long accountId = accountTokenResponse.getAccount().getId();
        final Long accountVerificationId = accountTokenResponse.getId();

        accountService.verifyAccount(accountId, accountVerificationId);
    }
}
