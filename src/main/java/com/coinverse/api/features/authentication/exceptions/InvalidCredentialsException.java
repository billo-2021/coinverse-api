package com.coinverse.api.features.authentication.exceptions;

import com.coinverse.api.core.exceptions.ApiException;
import com.coinverse.api.core.models.ApiErrorCode;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends ApiException {
    private static final ApiErrorCode API_ERROR_CODE = ApiErrorCode.INVALID_CREDENTIALS;
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;

    public InvalidCredentialsException() {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, HTTP_STATUS);
    }

    public InvalidCredentialsException(Throwable cause) {
        super(API_ERROR_CODE.getReason(), API_ERROR_CODE, HTTP_STATUS, cause);
    }
}
