package com.coinverse.api.common.utils;

import com.coinverse.api.common.security.exceptions.ApiAuthenticationException;
import com.coinverse.api.common.exceptions.ApiException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.errors.ErrorResponse;
import com.coinverse.api.common.errors.ErrorsResponse;
import com.coinverse.api.common.errors.ValidationErrorResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class ExceptionUtil {
    public static String getStackTrace(Exception ex) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);

        return stringWriter.toString();
    }

    public static ResponseEntity<ErrorResponse> getApiErrorResponse(ApiException apiEx) {
        final ErrorResponse errorResponse = ExceptionUtil.apiExceptionToErrorResponse(apiEx);

        return ResponseEntity
                .status(apiEx.getHttpStatus())
                .body(errorResponse);
    }

    public static ResponseEntity<ErrorsResponse> getApiErrorsResponse(
            ApiException apiException,
            List<? extends ApiException> apiExceptions
    ) {
        final List<ErrorResponse> errorResponses = apiExceptions.stream()
                .map(ExceptionUtil::apiExceptionToErrorResponse).toList();

        final ErrorsResponse errorsResponse = new ErrorsResponse(apiException, errorResponses);

        return ResponseEntity
                .status(errorsResponse.getHttpStatusCode())
                .body(errorsResponse);
    }

    public static ErrorResponse apiExceptionToErrorResponse(ApiException apiEx) {
        return apiEx instanceof ValidationException ?
                new ValidationErrorResponse((ValidationException) apiEx) :
                new ErrorResponse(apiEx);
    }

    public static ApiException apiAuthenticationExceptionToApiException(
            ApiAuthenticationException apiAuthenticationException) {
        return new ApiException(
                apiAuthenticationException.getMessage(),
                apiAuthenticationException.getApiErrorCode(),
                apiAuthenticationException.getHttpStatus()
        );
    }
}
