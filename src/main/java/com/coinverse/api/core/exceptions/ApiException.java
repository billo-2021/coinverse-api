package com.coinverse.api.core.exceptions;

import com.coinverse.api.core.models.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private static final ApiErrorCode DEFAULT_API_ERROR_CODE = ApiErrorCode.SOMETHING_WENT_WRONG;
    private static final HttpStatus DEFAULT_HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private final ApiErrorCode apiErrorCode;
    private final HttpStatus httpStatus;

    public ApiException() {
        this(DEFAULT_API_ERROR_CODE.getReason(), DEFAULT_API_ERROR_CODE, DEFAULT_HTTP_STATUS);
    }

    public ApiException(String message, ApiErrorCode apiErrorCode, HttpStatus httpStatus) {
        super(message);
        this.apiErrorCode = apiErrorCode;
        this.httpStatus = httpStatus;
    }

    public ApiException(String message, ApiErrorCode apiErrorCode, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.apiErrorCode = apiErrorCode;
        this.httpStatus = httpStatus;
    }

    public ApiErrorCode getApiErrorCode() {
        return apiErrorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
