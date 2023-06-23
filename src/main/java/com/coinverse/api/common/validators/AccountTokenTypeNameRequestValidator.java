package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountTokenTypeRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTokenTypeNameRequestValidator implements RequestValidator<String, AccountTokenType> {
    private final AccountTokenTypeRepository accountTokenTypeRepository;

    @Override
    public AccountTokenType validate(@NotNull String verificationMethodName) throws InvalidRequestException, MappingException {
        return accountTokenTypeRepository
                .findByName(verificationMethodName)
                .orElseThrow(() ->
                        new InvalidRequestException("Invalid verification method name '" + verificationMethodName + "'")
                );
    }
}
