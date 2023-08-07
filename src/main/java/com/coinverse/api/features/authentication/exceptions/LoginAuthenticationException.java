package com.coinverse.api.features.authentication.exceptions;

import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.security.exceptions.ApiAuthenticationException;
import com.coinverse.api.features.authentication.models.LoginRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.AuthenticationException;

public class LoginAuthenticationException extends ApiAuthenticationException {
    private final LoginRequest request;
    private final AccountResponse account;
    private final AuthenticationException exception;

    public LoginAuthenticationException(LoginRequest request,
                                        AccountResponse account,
                                        AuthenticationException exception) {
        this.request = request;
        this.account = account;
        this.exception = exception;
    }

    public LoginRequest getRequest() {
        return request;
    }

    public AccountResponse getAccount() {
        return account;
    }

    public AuthenticationException getException() {
        return exception;
    }
}
