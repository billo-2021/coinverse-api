package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.REQUEST_VALIDATION_ERROR;
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    public InvalidRequestException() {
        this(API_ERROR_CODE.getReason());
    }

    public InvalidRequestException(@NotNull final String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }
}
