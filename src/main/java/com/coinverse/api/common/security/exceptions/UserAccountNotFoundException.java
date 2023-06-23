package com.coinverse.api.common.security.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class UserAccountNotFoundException extends ApiAuthenticationException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.ACCOUNT_NOT_FOUND;
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public UserAccountNotFoundException() {
        this(API_ERROR_CODE.getReason());
    }

    public UserAccountNotFoundException(final @NotNull String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public UserAccountNotFoundException(final @NotNull HttpStatus httpStatus) {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, httpStatus);
    }

    public UserAccountNotFoundException(final @NotNull Throwable cause) {
        this(API_ERROR_CODE.getReason(), cause);
    }

    public UserAccountNotFoundException(final @NotNull HttpStatus httpStatus, final @NotNull String message) {
        super(message, API_ERROR_CODE, httpStatus);
    }

    public UserAccountNotFoundException(final @NotNull String message, final @NotNull Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
