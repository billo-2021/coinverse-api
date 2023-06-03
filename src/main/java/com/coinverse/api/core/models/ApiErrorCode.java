package com.coinverse.api.core.models;

public enum ApiErrorCode {
    SOMETHING_WENT_WRONG(0, "Something is broken"),
    SERVER_OVERLOADED(1, "The server is up, but overloaded with requests. Try again later"),
    ITEM_DOES_NOT_EXIST(2, "The item does not exist"),
    REQUEST_VALIDATION_ERROR(3, "Validation errors in your request"),
    INVALID_VALUE(4, "Oops! The value is invalid"),
    INVALID_FORMAT(5, "Oops! The format is not correct"),
    INVALID_CREDENTIALS(6, "Authentication credentials were missing or incorrect"),
    ITEM_CREATED(10, "The item was created successfully"),
    USER_WITH_EMAIL(20, "User with email already exists");

    private static final ApiErrorCode[] VALUES = values();
    private final int value;
    private final String reason;

    private ApiErrorCode(int value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public int value() {
        return this.value;
    }

    public String getReason() {
        return reason;
    }
}
