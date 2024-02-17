package com.coinverse.api.common.utils;

import com.coinverse.api.common.constants.ErrorMessage;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.NotFoundException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.security.exceptions.InvalidCredentialsException;

public class ErrorMessageUtils {
    public static InvalidRequestException getInvalidRequestException() {
        return new InvalidRequestException(ErrorMessage.INVALID_REQUEST.getMessage());
    }

    public static InvalidRequestException getInvalidRequestException(String fieldName) {
        return new InvalidRequestException(ErrorMessage.INVALID_REQUEST.getMessage() + ", " + fieldName);
    }

    public static InvalidRequestException getInvalidRequestException(ErrorMessage message) {
        return new InvalidRequestException(message.getMessage());
    }

    public static InvalidRequestException getInvalidRequestException(String fieldName, String fieldValue) {
        return new InvalidRequestException(ErrorMessage.INVALID_REQUEST.getMessage() + ", " + fieldName + " '" + fieldValue + "'");
    }

    public static MappingException getMappingException(String fieldName, String fieldValue) {
        return new MappingException(ErrorMessage.MAPPING_ERROR.getMessage() + " '" + fieldValue + "' " + fieldName);
    }

    public static ValidationException getValidationException(String fieldName, String fieldValue) {
        return new ValidationException(ErrorMessage.INVALID_REQUEST.getMessage() + "," + fieldName + ", '" + fieldValue + "'" );
    }

    public static ValidationException getValidationException(ErrorMessage message) {
        return new ValidationException(message.getMessage());
    }

    public static ValidationException getAlreadyExistValidationException(String modelName, String fieldName, String fieldValue) {
        return new ValidationException(ErrorMessage.INVALID_REQUEST.getMessage() + ", " + modelName + " with " + fieldName + " '" + fieldValue + "' already exist");
    }

    public static ValidationException getDoesNotExistValidationException(String modelName, String fieldName, String fieldValue) {
        return new ValidationException(ErrorMessage.INVALID_REQUEST.getMessage() + ", " + modelName + " with " + fieldName + " '" + fieldValue + "' does not exist");
    }

    public static MappingException getUnableToFindMappingException(String modelName, String fieldName, String fieldValue) {
        return new MappingException(ErrorMessage.MAPPING_ERROR.getMessage() + ", unable to find " + modelName + " with " + fieldName + "'" + fieldValue + "'");
    }

    public static NotFoundException getNotFoundException() {
        return new NotFoundException(ErrorMessage.NOT_FOUND.getMessage());
    }

    public static NotFoundException getUnableToFindNotFoundException(String modelName, String fieldName, String fieldValue) {
        return new NotFoundException(ErrorMessage.NOT_FOUND.getMessage() + ", unable to find " + modelName + " with " + fieldName + "'" + fieldValue + "'");
    }

    public static InvalidCredentialsException getInvalidCredentialsException() {
        return new InvalidCredentialsException(ErrorMessage.INVALID_CREDENTIALS.getMessage());
    }
}
