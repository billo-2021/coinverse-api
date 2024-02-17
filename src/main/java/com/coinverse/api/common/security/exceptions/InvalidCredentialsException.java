package com.coinverse.api.common.security.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiAuthenticationException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.INVALID_CREDENTIALS;
    private static final HttpStatus HTTP_STATUS = HttpStatus.FORBIDDEN;

    public InvalidCredentialsException() {
        this(API_ERROR_CODE.getReason());
    }

    public InvalidCredentialsException(String message) {
        super(message, API_ERROR_CODE, HTTP_STATUS);
    }

    public InvalidCredentialsException(HttpStatus httpStatus) {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, httpStatus);
    }

    public InvalidCredentialsException(Throwable cause) {
        this(API_ERROR_CODE.getReason(), cause);
    }

    public InvalidCredentialsException(HttpStatus httpStatus, String message) {
        super(message, API_ERROR_CODE, httpStatus);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
