package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class TokenInvalidException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.INVALID_VALUE;
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    public TokenInvalidException() {
        this(API_ERROR_CODE.getReason());
    }

    public TokenInvalidException(@NotNull final String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }
}
