package com.coinverse.api.common.security.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class JwtTokenExpiredException extends ApiAuthenticationException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.INVALID_CREDENTIALS;
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public JwtTokenExpiredException() {
        this(API_ERROR_CODE.getReason());
    }

    public JwtTokenExpiredException(final @NotNull String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public JwtTokenExpiredException(final @NotNull HttpStatus httpStatus) {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, httpStatus);
    }

    public JwtTokenExpiredException(final @NotNull Throwable cause) {
        this(API_ERROR_CODE.getReason(), cause);
    }

    public JwtTokenExpiredException(final @NotNull HttpStatus httpStatus, final @NotNull String message) {
        super(message, API_ERROR_CODE, httpStatus);
    }

    public JwtTokenExpiredException(final @NotNull String message, final @NotNull Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
