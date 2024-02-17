package com.coinverse.api.common.constants;

public enum ErrorMessage {
    INVALID_REQUEST("Failed: Invalid request"),
    MAPPING_ERROR("Failed: Error while mapping"),
    NOT_FOUND("Failed: Not found"),
    MIN_PAGE_NUMBER_VALIDATION("Failed: Invalid page request, pageNumber must be greater than or equal to 0"),
    MIN_PAGE_SIZE_VALIDATION("Failed: Invalid page request, pageSize must be greater than or equal to 1"),
    MAX_PAGE_SIZE_VALIDATION("Failed: Invalid page request, pageSize must be less than or equal to 100"),
    INVALID_CREDENTIALS("Failed: Invalid credentials provided"),
    QUOTE_EXPIRED("Failed: Quote has expired"),
    INSUFFICIENT_FUNDS("Failed: Insufficient funds in account");
    private final String message;

    private ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
