package com.coinverse.api.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
//    @ExceptionHandler
//    public ResponseEntity<ErrorMessage> handleInvalidCredentialsException(
//            BadCredentialsException ex
//    ) {
//        return new ResponseEntity<ErrorMessage>(new ErrorMessage(e.getMessage()), HttpStatus.UNAUTHORIZED)
//    }
//
//    @Data
//    @AllArgsConstructor
//    static
//    class ErrorMessage extends RuntimeException {
//        private String error;
//    }
}
