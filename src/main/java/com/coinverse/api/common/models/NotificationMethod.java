package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum NotificationMethod {
    SMS("sms"),
    EMAIL("email"),
    PUSH("push");
    private final String name;

    private NotificationMethod(final @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<NotificationMethod> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(verificationMethod ->
                        verificationMethod.getName().equalsIgnoreCase(name)
                ).findFirst();
    }
}
