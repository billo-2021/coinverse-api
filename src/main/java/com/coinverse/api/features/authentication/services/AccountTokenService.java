package com.coinverse.api.features.authentication.services;

import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.TokenExpiredException;
import com.coinverse.api.common.exceptions.TokenInvalidException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.services.StringTokenGenerator;
import com.coinverse.api.common.services.StringTokenService;
import com.coinverse.api.features.authentication.models.GeneratedTokenMessage;
import com.coinverse.api.features.authentication.models.TokenRequest;
import com.coinverse.api.features.authentication.models.TokenVerifyRequest;
import com.coinverse.api.features.messaging.models.Message;
import com.coinverse.api.features.messaging.models.MessagingChannel;
import com.coinverse.api.features.messaging.services.MessagingService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccountTokenService {
    private static final Logger logger = LoggerFactory.getLogger(AccountVerificationService.class);

    private final StringTokenService stringTokenService;
    private final MessagingService messagingService;
    private final AccountService accountService;

    public void requestToken(@NotNull final Long accountId,
                             @NotNull final String tokenTypeName,
                             @NotNull final TokenRequest tokenRequest,
                             @NotNull final StringTokenGenerator stringTokenGenerator,
                             @NotNull final GeneratedTokenMessage generatedTokenMessage) {

        final StringToken token = accountService.getAccountTokenByAccountIdAndTokenTypeName(accountId,
                        tokenTypeName)
                .map((accountTokenResponse) -> generateTokenFromExistingToken(accountTokenResponse, stringTokenGenerator))
                .orElseGet(() -> stringTokenService.generate(stringTokenGenerator));

        logger.info("Generated Token: {}", token.getKey());

        final com.coinverse.api.common.models.AccountTokenRequest accountTokenRequest = com.coinverse.api.common.models.AccountTokenRequest
                .builder()
                .type(tokenTypeName)
                .key(token.getKey())
                .expiresAt(token.getExpiresAt())
                .build();

        accountService.addAccountTokenByAccountId(accountId, accountTokenRequest);

        final Message message = generatedTokenMessage.getMessage(token);

        final MessagingChannel messagingChannel = MessagingChannel
                .of(tokenRequest.getMessagingChannel())
                .orElseThrow(() ->
                        new ValidationException("Invalid messaging channel '" + tokenRequest.getMessagingChannel() + "'", "messagingChannel")
                );

        final Set<MessagingChannel> messagingChannels = Set.of(messagingChannel);

        messagingService.sendMessage(accountId, message, messagingChannels);
    }

    public AccountTokenResponse verifyToken(@NotNull final Long accountId,
                                            @NotNull final String tokenTypeName,
                                            @NotNull final TokenVerifyRequest verifyTokenRequest) {

        final AccountTokenResponse accountTokenResponse = accountService
                .getAccountTokenByAccountIdAndTokenTypeName(accountId, tokenTypeName)
                .orElseThrow(InvalidRequestException::new);

        final Long accountVerificationId = accountTokenResponse.getId();

        final StringToken stringToken = new StringToken(
                accountTokenResponse.getKey(),
                accountTokenResponse.getExpiresAt());

        try {
            stringTokenService.validateToken(stringToken, verifyTokenRequest.getToken());
        } catch(TokenExpiredException | TokenInvalidException ex) {
            accountService.addAccountTokenUsageAttemptByTokenId(accountVerificationId);
            throw ex;
        }

        return accountTokenResponse;
    }

    private StringToken generateTokenFromExistingToken(@NotNull final AccountTokenResponse accountTokenResponse,
                                                       @NotNull final StringTokenGenerator stringTokenGenerator) {
        final StringToken token = new StringToken(accountTokenResponse.getKey(),
                accountTokenResponse.getExpiresAt());

        if (!token.hasExpired()) {
            return new StringToken(
                    token.getKey(),
                    token.getExpiresAt());
        }

        return stringTokenService.generate(stringTokenGenerator);
    }
}
