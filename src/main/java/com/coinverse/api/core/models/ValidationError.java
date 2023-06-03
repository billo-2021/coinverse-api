package com.coinverse.api.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

public class ValidationError {
    @JsonIgnore
    private static final ApiErrorCode DEFAULT_CODE = ApiErrorCode.REQUEST_VALIDATION_ERROR;
    @JsonIgnore
    private static final HttpStatus DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST;

    private String message;
    private String fieldName;
    private int code;

    public ValidationError() {

    }
}
