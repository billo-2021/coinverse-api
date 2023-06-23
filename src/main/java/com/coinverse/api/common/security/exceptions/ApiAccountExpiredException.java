package com.coinverse.api.common.security.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class ApiAccountExpiredException extends ApiAuthenticationException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.ACCOUNT_EXPIRED;
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public ApiAccountExpiredException() {
        this(API_ERROR_CODE.getReason());
    }

    public ApiAccountExpiredException(@NotNull final String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public ApiAccountExpiredException(@NotNull final HttpStatus httpStatus) {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, httpStatus);
    }

    public ApiAccountExpiredException(@NotNull final Throwable cause) {
        this(API_ERROR_CODE.getReason(), cause);
    }

    public ApiAccountExpiredException(@NotNull final HttpStatus httpStatus, @NotNull final String message) {
        super(message, API_ERROR_CODE, httpStatus);
    }

    public ApiAccountExpiredException(@NotNull final String message, @NotNull final Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
