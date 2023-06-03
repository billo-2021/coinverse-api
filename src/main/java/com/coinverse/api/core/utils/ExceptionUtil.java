package com.coinverse.api.core.utils;

import com.coinverse.api.core.models.ApiErrorCode;
import com.coinverse.api.core.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    public static String getStackTrace(Exception ex) {
        var stringWriter = new StringWriter();
        var printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        return stringWriter.toString();
    }

    public static ResponseEntity<ErrorResponse> getExceptionResponse(
            String message,
            ApiErrorCode apiErrorCode,
            HttpStatus httpStatus,
            String stackTrace
    ) {
        var errorResponse = new ErrorResponse(
                message,
                apiErrorCode,
                httpStatus,
                stackTrace
        );

        return ResponseEntity
                .status(errorResponse.getHttpCode())
                .body(errorResponse);
    }
}
