package com.coinverse.api.core.exceptions;

import com.coinverse.api.core.models.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidRequestException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.REQUEST_VALIDATION_ERROR;
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    public InvalidRequestException() {
        this(API_ERROR_CODE.getReason());
    }

    public InvalidRequestException(String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }
}
