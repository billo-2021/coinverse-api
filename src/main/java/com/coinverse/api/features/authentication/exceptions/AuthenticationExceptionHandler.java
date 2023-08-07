package com.coinverse.api.features.authentication.exceptions;

import com.coinverse.api.common.errors.ErrorResponse;
import com.coinverse.api.common.security.exceptions.ApiAuthenticationException;
import com.coinverse.api.common.utils.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationExceptionHandler {

    @ExceptionHandler(ApiAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleApiAuthenticationException(
            final ApiAuthenticationException apiAuthEx) {
        return ExceptionUtil.getApiErrorResponse(apiAuthEx);
    }
}
