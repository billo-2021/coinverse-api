package com.coinverse.api.common.controllers;

import com.coinverse.api.common.errors.ErrorResponse;
import com.coinverse.api.common.exceptions.NotFoundException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorControllerImpl implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError() {
        NotFoundException notFoundException = new NotFoundException();

        ErrorResponse errorResponse = new ErrorResponse(notFoundException);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
