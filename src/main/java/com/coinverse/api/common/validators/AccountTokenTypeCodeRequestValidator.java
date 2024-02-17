package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountTokenTypeRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTokenTypeCodeRequestValidator implements RequestValidator<String, AccountTokenType> {
    private final AccountTokenTypeRepository accountTokenTypeRepository;

    @Override
    public AccountTokenType validate(String verificationMethod) throws InvalidRequestException, MappingException {
        return accountTokenTypeRepository
                .findByCodeIgnoreCase(verificationMethod)
                .orElseThrow(() ->
                        ErrorMessageUtils.getInvalidRequestException("verificationMethodName", verificationMethod)
                );
    }
}
