package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.entities.User;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.AccountRepository;
import com.coinverse.api.common.repositories.AccountTokenRepository;
import com.coinverse.api.common.repositories.UserRepository;
import com.coinverse.api.features.authentication.models.*;
import com.coinverse.api.features.authentication.validators.ResetPasswordValidator;
import com.coinverse.api.features.messaging.models.Message;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ResetPasswordService {
    private static final String TOKEN_TYPE_CODE = AccountTokenTypeEnum.RESET_PASSWORD_LINK_TOKEN.getCode();

    private final AccountRepository accountRepository;
    private final AccountTokenRepository accountTokenRepository;
    private final UserRepository userRepository;

    private final AccountTokenService accountTokenService;
    private final ResetPasswordTokenGenerator resetPasswordTokenGenerator;
    private final ResetPasswordValidator resetPasswordValidator;

    public ResetPasswordTokenResponse requestResetPasswordToken(@NotNull final TokenRequest tokenRequest) {
        final Account account = resetPasswordValidator.validateRequestResetPasswordToken(tokenRequest);
        final User user = getAccountUser(account);

        accountTokenService.requestToken(
                account.getId(),
                TOKEN_TYPE_CODE,
                tokenRequest,
                resetPasswordTokenGenerator,
                (token) -> Message
                        .builder()
                        .subject("Reset Password")
                        .content("Hi, " + user.getFirstName() + " " + user.getLastName() + "\n\n" +
                                "Use link: " + "http://localhost:4200/authentication/reset-password/" +
                                token.getKey() + "\n\nTo reset your password." + "\n\nKind regards, Coinverse Team.")
                        .build()
                );

        return ResetPasswordTokenResponse
                .builder()
                .username(account.getUsername())
                .emailAddress(user.getEmailAddress())
                .build();
    }

    public PasswordTokenUserResponse getPasswordTokenUser(final String token) {
        final AccountToken accountToken = resetPasswordValidator.validateTokenKey(token);

        final Account account = accountToken.getAccount();
        final User user = getAccountUser(account);

        return PasswordTokenUserResponse
                .builder()
                .username(account.getUsername())
                .emailAddress(user.getEmailAddress())
                .build();
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
                TOKEN_TYPE_CODE,
                tokenVerifyRequest
        );

        accountRepository.save(account);
        accountTokenRepository.deleteById(accountTokenResponse.getId());
    }

    private User getAccountUser(final Account account) {
        return userRepository.findByAccountId(account.getId())
                .orElseThrow(()
                        -> new MappingException("Invalid username '" + account.getUsername() + "'"));
    }
}
