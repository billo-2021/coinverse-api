package com.coinverse.api.common.security.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class MissingCredentialsException extends ApiAuthenticationException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.INVALID_CREDENTIALS;
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public MissingCredentialsException() {
        this(API_ERROR_CODE.getReason());
    }

    public MissingCredentialsException(final @NotNull String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public MissingCredentialsException(HttpStatus httpStatus) {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, httpStatus);
    }

    public MissingCredentialsException(final @NotNull Throwable cause) {
        this(API_ERROR_CODE.getReason(), cause);
    }

    public MissingCredentialsException(final @NotNull HttpStatus httpStatus, final @NotNull String message) {
        super(message, API_ERROR_CODE, httpStatus);
    }

    public MissingCredentialsException(final @NotNull String message, final @NotNull Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
