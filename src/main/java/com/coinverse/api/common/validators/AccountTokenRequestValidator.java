package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.AccountTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTokenRequestValidator implements RequestValidator<AccountTokenRequest, AccountToken> {
    private final AccountTokenTypeCodeRequestValidator accountTokenTypeNameRequestValidator;

    @Override
    public AccountToken validate(AccountTokenRequest accountVerificationRequest) throws InvalidRequestException, MappingException {
        final AccountTokenType accountTokenType = accountTokenTypeNameRequestValidator.validate(accountVerificationRequest.getType());

        return AccountToken
                .builder()
                .type(accountTokenType)
                .key(accountVerificationRequest.getKey())
                .expiresAt(accountVerificationRequest.getExpiresAt())
                .numberOfUsageAttempts(0)
                .build();
    }
}
