package com.coinverse.api.common.errors;

import com.coinverse.api.common.exceptions.ValidationException;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ValidationErrorResponse extends ErrorResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String fieldName;
    public ValidationErrorResponse(ValidationException ex) {
        this(ex, false);
    }

    public ValidationErrorResponse(ValidationException ex, boolean showTimeStamp) {
        super(ex, showTimeStamp);
        this.fieldName = ex.getFieldName();
    }

    public String getFieldName() {
        return fieldName;
    }
}
