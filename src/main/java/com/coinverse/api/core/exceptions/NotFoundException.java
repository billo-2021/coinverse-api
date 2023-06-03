package com.coinverse.api.core.exceptions;

import com.coinverse.api.core.models.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.ITEM_DOES_NOT_EXIST;
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    public NotFoundException() {
        this(API_ERROR_CODE.getReason());
    }

    public NotFoundException(String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }
}
