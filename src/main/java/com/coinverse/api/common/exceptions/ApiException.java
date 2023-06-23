package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private static final ApiErrorCode DEFAULT_API_ERROR_CODE = ApiErrorCode.SOMETHING_WENT_WRONG;
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private final ApiErrorCode apiErrorCode;
    private final HttpStatus httpStatus;

    public ApiException() {
        this(DEFAULT_API_ERROR_CODE.getReason(), DEFAULT_API_ERROR_CODE, DEFAULT_HTTP_STATUS);
    }

    public ApiException(@NotNull final String message) {
        this(message, DEFAULT_API_ERROR_CODE, DEFAULT_HTTP_STATUS);
    }

    public ApiException(@NotNull final ApiErrorCode apiErrorCode) {
        this(DEFAULT_API_ERROR_CODE.getReason(), apiErrorCode, DEFAULT_HTTP_STATUS);
    }

    public ApiException(@NotNull final HttpStatus httpStatus) {
        this(DEFAULT_API_ERROR_CODE.getReason(), DEFAULT_API_ERROR_CODE, httpStatus);
    }

    public ApiException(@NotNull final Throwable cause) {
        this(DEFAULT_API_ERROR_CODE.getReason(), DEFAULT_API_ERROR_CODE, DEFAULT_HTTP_STATUS, cause);
    }

    public ApiException(@NotNull final String message,
                        @NotNull final ApiErrorCode apiErrorCode,
                        @NotNull final HttpStatus httpStatus) {
        super(message);
        this.apiErrorCode = apiErrorCode;
        this.httpStatus = httpStatus;
    }

    public ApiException(@NotNull final String message,
                        @NotNull final ApiErrorCode apiErrorCode,
                        @NotNull final HttpStatus httpStatus,
                        @NotNull final Throwable cause) {
        super(message, cause);
        this.apiErrorCode = apiErrorCode;
        this.httpStatus = httpStatus;
    }

    public ApiErrorCode getApiErrorCode() {
        return apiErrorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
