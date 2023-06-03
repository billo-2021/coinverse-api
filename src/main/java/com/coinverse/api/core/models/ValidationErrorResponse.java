package com.coinverse.api.core.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationErrorResponse extends ErrorResponse {
    @JsonIgnore
    private static final ApiErrorCode DEFAULT_CODE = ApiErrorCode.REQUEST_VALIDATION_ERROR;
    @JsonIgnore
    private static final HttpStatus DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST;
    private List<> errors;

    public ValidationErrorResponse(List<ValidationErrorResponse> errors) {
        super(DEFAULT_CODE.getReason(), DEFAULT_CODE, DEFAULT_HTTP_CODE);

    }
}
