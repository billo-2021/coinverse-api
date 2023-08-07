package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum AccountTokenTypeEnum {
    OTP_TOKEN("otp_token", "OTP Token"),
    VERIFICATION_TOKEN("verification_token", "Verification Token"),
    PASSWORD_TOKEN("password_token", "Password Token"),
    VERIFICATION_LINK_TOKEN("verification_link_token", "Verification Link Token"),
    RESET_PASSWORD_LINK_TOKEN("reset_password_link_token", "Reset Password Link Token");

    private final String code;
    private final String name;

    private AccountTokenTypeEnum(String code,String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }

    public String getName() {
        return name;
    }

    public static Optional<AccountTokenTypeEnum> of(final @NotNull String code) {
        return Stream.of(values())
                .filter(accountVerificationMethodEnum
                        -> accountVerificationMethodEnum.getCode().equals(code))
                .findFirst();
    }
}
