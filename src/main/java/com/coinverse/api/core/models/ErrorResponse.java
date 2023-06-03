package com.coinverse.api.core.models;

import com.coinverse.api.core.exceptions.ApiException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ErrorResponse {
    private String timeStamp;
    private String message;
    private int code;
//    @JsonIgnore
//    private int httpCode;
    @JsonIgnore
    private String stackTrace;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<Object> errors = null;

    public ErrorResponse() {
        this(DEFAULT_CODE.getReason(), DEFAULT_CODE, DEFAULT_HTTP_CODE);
    }

    public ErrorResponse(ApiException apiException) {
        timeStamp = getCurrentTimeStamp();
        message = apiException.getMessage();
        code = apiException.getApiErrorCode().value();
    }

    public ErrorResponse(String message, ApiErrorCode code, HttpStatus httpStatusCode) {
        this(message, code, httpStatusCode, "");
    }

    public ErrorResponse(String message, ApiErrorCode code, HttpStatus httpStatusCode, String stackTrace) {
        this(message, code, httpStatusCode, stackTrace, null);
    }

    public ErrorResponse(
            String message,
            ApiErrorCode code,
            HttpStatus httpStatusCode,
            String stackTrace,
            Object data
    ) {
        this.timeStamp = getCurrentTimeStamp();
        this.message = message;
        this.code = code.value();
        this.httpCode = httpStatusCode.value();
        this.stackTrace = stackTrace;
        this.data = data;
    }

    private String getCurrentTimeStamp() {
        return ZonedDateTime
                .now(ZoneOffset.UTC)
                .format(DateTimeFormatter.ISO_INSTANT);
    }
}
