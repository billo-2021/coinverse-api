package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum AccountStatusEnum {
    PENDING_VERIFICATION("pending_verification"),
    VERIFIED("verified"),
    LOCKED("locked"),
    CREDENTIALS_EXPIRED("credentials_expired"),
    EXPIRED("expired"),
    DELETED("deleted");
    private final String name;

    private AccountStatusEnum(final @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<AccountStatusEnum> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(accountStatusEnum ->
                        accountStatusEnum.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
