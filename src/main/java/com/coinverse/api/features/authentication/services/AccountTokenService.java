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
import com.coinverse.api.features.messaging.models.MessagingChannelEnum;
import com.coinverse.api.features.messaging.services.MessagingService;
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

    public void requestToken(Long accountId,
                             String tokenTypeName,
                             TokenRequest tokenRequest,
                             StringTokenGenerator stringTokenGenerator,
                             GeneratedTokenMessage generatedTokenMessage) {

        final StringToken token = accountService.getAccountTokenByAccountIdAndTokenTypeCode(accountId,
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

        final MessagingChannelEnum messagingChannelEnum = MessagingChannelEnum
                .of(tokenRequest.getMessagingChannel())
                .orElseThrow(() ->
                        new ValidationException("Invalid messaging channel '" + tokenRequest.getMessagingChannel() + "'", "messagingChannel")
                );

        final Set<MessagingChannelEnum> messagingChannelEnums = Set.of(messagingChannelEnum);

        messagingService.sendMessage(accountId, message, messagingChannelEnums);
    }

    public AccountTokenResponse verifyToken(Long accountId,
                                            String tokenTypeCode,
                                            TokenVerifyRequest verifyTokenRequest) {

        final AccountTokenResponse accountTokenResponse = accountService
                .getAccountTokenByAccountIdAndTokenTypeCode(accountId, tokenTypeCode)
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

    private StringToken generateTokenFromExistingToken(AccountTokenResponse accountTokenResponse,
                                                       StringTokenGenerator stringTokenGenerator) {
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
