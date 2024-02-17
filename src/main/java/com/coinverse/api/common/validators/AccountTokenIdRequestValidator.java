package com.coinverse.api.common.validators;

import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.repositories.AccountTokenRepository;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountTokenIdRequestValidator implements RequestValidator<Long, AccountToken> {
    private final AccountTokenRepository accountTokenRepository;

    @Override
    public AccountToken validate(Long id) throws InvalidRequestException, MappingException {
        return accountTokenRepository.findById(id)
                .orElseThrow(() ->
                        ErrorMessageUtils.getInvalidRequestException("verificationId", String.valueOf(id))
                );
    }
}
