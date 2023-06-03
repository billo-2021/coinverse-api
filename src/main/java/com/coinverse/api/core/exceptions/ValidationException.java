package com.coinverse.api.core.exceptions;

import com.coinverse.api.core.models.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.REQUEST_VALIDATION_ERROR;
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    private final String fieldName;

    ValidationException(String fieldName, String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
        this.fieldName = fieldName;
    }

    ValidationException(String fieldName, String message, Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
