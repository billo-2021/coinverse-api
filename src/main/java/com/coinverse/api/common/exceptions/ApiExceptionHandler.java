package com.coinverse.api.common.exceptions;

import com.coinverse.api.common.errors.ApiErrorCode;
import com.coinverse.api.common.errors.ErrorResponse;
import com.coinverse.api.common.errors.ErrorsResponse;
import com.coinverse.api.common.utils.ExceptionUtil;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(final ApiException ex) {
        return ExceptionUtil.getApiErrorResponse(ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleValidationException(
            final MethodArgumentNotValidException ex) {

        final ValidationException apiException = new ValidationException();

        final List<ValidationException> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(this::objectErrorToValidationException)
                .toList();

        return ExceptionUtil.getApiErrorsResponse(
                apiException,
                errors
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(
            final MissingServletRequestParameterException ex) {

        return ExceptionUtil.getApiErrorResponse(
                new InvalidRequestException(ex.getMessage())
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException ex) {

        return ExceptionUtil.getApiErrorResponse(
                new InvalidRequestException(ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex) {
        final ValidationException validationException = new ValidationException();

        return ExceptionUtil.getApiErrorResponse(validationException);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        NotFoundException notFoundException = new NotFoundException();

        return ExceptionUtil.getApiErrorResponse(notFoundException);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        return ExceptionUtil.getApiErrorResponse(
                new InvalidRequestException(ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception ex) {
        logger.error(ex.getMessage(), ex);

        final ApiErrorCode apiErrorCode = ApiErrorCode.SOMETHING_WENT_WRONG;
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ApiException apiEx = new ApiException(apiErrorCode.getReason(), apiErrorCode, httpStatus, ex);

        return ExceptionUtil.getApiErrorResponse(apiEx);
    }

    private ValidationException objectErrorToValidationException(final ObjectError error) {
        final String fieldName = ((FieldError) error).getField();
        final String errorMessage = error.getDefaultMessage();

        return new ValidationException(errorMessage, fieldName);
    }
}
