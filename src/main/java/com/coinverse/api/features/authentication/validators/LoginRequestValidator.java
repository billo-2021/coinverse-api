package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.common.validators.RequestValidator;
import com.coinverse.api.features.authentication.models.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginRequestValidator implements RequestValidator<LoginRequest, AccountResponse> {
    private final AccountService accountService;

    @Override
    public AccountResponse validate(LoginRequest loginRequest) throws InvalidRequestException, MappingException {
        final String username = loginRequest.getUsername();

        return accountService.getAccountByUsername(username)
                .orElseThrow(ErrorMessageUtils::getInvalidCredentialsException);
    }
}
