package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

public class ValidationException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.REQUEST_VALIDATION_ERROR;
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String fieldName;

    public ValidationException() {
        this(API_ERROR_CODE.getReason());
    }

    public ValidationException(final String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public ValidationException(final String message, final String fieldName) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
        this.fieldName = fieldName;
    }

    public ValidationException(final String message, final String fieldName, final Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
