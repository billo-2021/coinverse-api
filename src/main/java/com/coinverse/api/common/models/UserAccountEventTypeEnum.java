package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum UserAccountEventTypeEnum {
    LOGIN_ATTEMPT_FAILURE("Login_attempt_failure", "Login Attempt Failure", "Login Attempt Failed"),
    LOGIN_ATTEMPT_SUCCESS("login_attempt_success", "Login Attempt Success", "Logged in Successfully"),
    PROFILE_UPDATE("profile_update", "Profile Update", "Updated Profile"),
    ROLE_UPDATE("role_update", "Role Update", "Updated Role"),
    USER_ADDRESS_UPDATE("address_updated", "Address Update", "Updated Address"),
    USER_PREFERENCE_UPDATE("user_preference_update", "User Preference Update", "Updated Preference"),
    PASSWORD_UPDATE("password_update", "Password Update", "Updated Password");

    private final String code;

    private final String name;
    private final String description;

    private UserAccountEventTypeEnum(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }
    public String getDescription() { return description; }

    public static Optional<UserAccountEventTypeEnum> of(String code) {
        return Stream.of(values())
                .filter(userAccountEventTypeEnum ->
                        userAccountEventTypeEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
