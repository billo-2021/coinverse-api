package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ErrorResponse;
import org.springframework.http.ResponseEntity;

public interface GenericExceptionHandler<T extends Exception> {
    ResponseEntity<ErrorResponse> handle(final T exception);
}
