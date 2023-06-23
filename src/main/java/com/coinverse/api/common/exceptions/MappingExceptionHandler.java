package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ErrorResponse;
import com.coinverse.api.common.utils.ExceptionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MappingExceptionHandler implements GenericExceptionHandler<MappingException> {

    @ExceptionHandler(MappingException.class)
    @Override
    public ResponseEntity<ErrorResponse> handle(final MappingException exception) {
        return ExceptionUtil.getApiErrorResponse(exception);
    }
}
