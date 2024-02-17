package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.RESOURCE_NOT_FOUND;
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    public NotFoundException() {
        this(API_ERROR_CODE.getReason());
    }

    public NotFoundException(String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }
}
