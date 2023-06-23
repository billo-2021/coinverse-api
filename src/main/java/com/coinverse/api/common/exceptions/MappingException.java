package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

public class MappingException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.ITEM_DOES_NOT_EXIST;
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public MappingException() {
        this(API_ERROR_CODE.getReason());
    }

    public MappingException(@NotNull final String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public MappingException(@NotNull final String message, @NotNull final Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
