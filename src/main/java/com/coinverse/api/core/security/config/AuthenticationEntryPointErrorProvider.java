package com.coinverse.api.core.security.config;

import com.coinverse.api.common.exceptions.ApiException;
import com.coinverse.api.common.security.exceptions.ApiAuthenticationException;
import com.coinverse.api.common.errors.ErrorResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationEntryPointErrorProvider {
    private ErrorResponse errorResponse;

    public AuthenticationEntryPointErrorProvider() {
        this(new ApiAuthenticationException());
    }

    public AuthenticationEntryPointErrorProvider(ApiException apiException) {
        errorResponse = new ErrorResponse(apiException);
    }

    ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(@NotNull final ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public void setErrorResponse(@NotNull final ApiException apiException) {
        this.errorResponse = new ErrorResponse(apiException);
    }

    public void resetError() {
        this.errorResponse = new ErrorResponse(new ApiAuthenticationException());
    }
}
