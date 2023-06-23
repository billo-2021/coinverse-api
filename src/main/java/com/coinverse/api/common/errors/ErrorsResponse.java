package com.coinverse.api.common.errors;

import com.coinverse.api.common.exceptions.ApiException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ErrorsResponse extends ErrorResponse {
    @JsonIgnore
    private static final HttpStatus DEFAULT_HTTP_CODE = HttpStatus.BAD_REQUEST;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<ErrorResponse> errors;

    public ErrorsResponse(@NotNull final List<ErrorResponse> errors) {
        this(new ApiException(), errors);
    }

    public ErrorsResponse(
            @NotNull final ApiException apiException,
            @NotNull final List<ErrorResponse> errors
    ) {
        this(apiException, errors,true);
    }

    public ErrorsResponse(
            @NotNull final ApiException apiException,
            @NotNull final List<ErrorResponse> errors,
            boolean showTimeStamp
    ) {
        super(apiException, showTimeStamp);
        this.errors = errors;
    }

    public List<ErrorResponse> getErrors() {
        return errors;
    }
}
