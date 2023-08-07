package com.coinverse.api.common.constants;

public enum ApiMessage {
    ACCOUNT_VERIFICATION_TOKEN_SENT("Account verification token sent"),
    ACCOUNT_VERIFIED("Account verified"),
    ACCOUNT_PASSWORD_RESET("Account password reset"),
    ACCOUNT_DISABLED_SUCCESS("Account account disabled successfully"),
    ACCOUNT_ENABLED_SUCCESS("Account account enabled successfully"),
    USER_ADDED("User added"),
    USER_PROFILE_UPDATED("User profile updated"),
    USER_ACCOUNT_PASSWORD_UPDATED("User account password updated");
    private final String message;

    private ApiMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
