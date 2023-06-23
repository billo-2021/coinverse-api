package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum AccountTokenTypeEnum {
    OTP_TOKEN("otp_token"),
    VERIFICATION_TOKEN("verification_token"),
    PASSWORD_TOKEN("password_token"),
    VERIFICATION_LINK_TOKEN("verification_link_token"),
    RESET_PASSWORD_LINK_TOKEN("reset_password_link_token");

    private final String name;

    private AccountTokenTypeEnum(final @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<AccountTokenTypeEnum> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(accountVerificationMethodEnum
                        -> accountVerificationMethodEnum.getName().equals(name))
                .findFirst();
    }
}
