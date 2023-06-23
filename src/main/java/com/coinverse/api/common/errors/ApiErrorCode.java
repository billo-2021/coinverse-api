package com.coinverse.api.common.errors;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum ApiErrorCode {
    SOMETHING_WENT_WRONG(0, "Something is broken"),
    SERVER_OVERLOADED(1, "The server is up, but overloaded with requests. Try again later"),
    RESOURCE_NOT_FOUND(2, "Requested resource was not found"),
    ITEM_DOES_NOT_EXIST(3, "The item does not exist"),
    REQUEST_VALIDATION_ERROR(4, "Validation errors in your request"),
    INVALID_VALUE(5, "Oops! The value is invalid"),
    INVALID_FORMAT(6, "Oops! The format is not correct"),
    EXPIRED_VALUE(7, "Oops! The value has expired"),
    INVALID_CREDENTIALS(8, "Authentication credentials were missing or incorrect"),
    VERIFICATION_REQUIRED(9, "User account verification is required"),
    ACCOUNT_DISABLED(10, "User account is disabled"),
    ACCOUNT_LOCKED(11, "User account is locked"),
    ACCOUNT_EXPIRED(12, "User account expired"),
    CREDENTIALS_EXPIRED(13, "User account credentials expired"),
    RESOURCE_ACCESS_DENIED(14, "You are not allowed to access this resource"),
    ACCOUNT_NOT_FOUND(15, "User account not found"),
    ITEM_CREATED(16, "The item was created successfully"),
    USER_WITH_EMAIL(20, "User with email already exists");

    private static final ApiErrorCode[] VALUES = values();
    private final int value;
    private final String reason;

    private ApiErrorCode(int value, @NotNull final String reason) {
        this.value = value;
        this.reason = reason;
    }

    public int value() {
        return this.value;
    }

    public String getReason() {
        return reason;
    }

    public static Optional<ApiErrorCode> of(int code) {
        return Stream.of(values())
                .filter(apiErrorCode -> apiErrorCode.value() == code)
                .findFirst();
    }
}
