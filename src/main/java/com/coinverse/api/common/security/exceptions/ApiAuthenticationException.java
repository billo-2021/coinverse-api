package com.coinverse.api.common.security.exceptions;

import com.coinverse.api.common.exceptions.ApiException;
import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class ApiAuthenticationException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.INVALID_CREDENTIALS;
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public ApiAuthenticationException() {
        this(HTTP_STATUS);
    }

    public ApiAuthenticationException(final @NotNull HttpStatus httpStatus) {
        this(API_ERROR_CODE.getReason(), API_ERROR_CODE, httpStatus);
    }

    public ApiAuthenticationException(final @NotNull Throwable cause) {
        this(API_ERROR_CODE.getReason(), API_ERROR_CODE, HTTP_STATUS, cause);
    }

    public ApiAuthenticationException(final @NotNull String message,
                                      final @NotNull ApiErrorCode apiErrorCode,
                                      final @NotNull HttpStatus httpStatus) {
        super(message, apiErrorCode, httpStatus);
    }

    public ApiAuthenticationException(final @NotNull String message,
                                      final @NotNull ApiErrorCode apiErrorCode,
                                      final @NotNull HttpStatus httpStatus,
                                      final @NotNull Throwable cause) {
        super(message, apiErrorCode, httpStatus, cause);
    }
}
