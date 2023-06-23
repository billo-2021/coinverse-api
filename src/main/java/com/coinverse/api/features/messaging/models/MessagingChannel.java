package com.coinverse.api.features.messaging.models;

import com.coinverse.api.common.validators.EnumValidatorComparator;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.stream.Stream;

@Validated
public enum MessagingChannel implements EnumValidatorComparator<String> {
    PUSH("push"),
    EMAIL("email"),
    SMS("sms");

    private final String name;

    private MessagingChannel(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<MessagingChannel> of(@NotNull final String name) {
        return Stream.of(values())
                .filter(messagingChannel -> messagingChannel.test(name))
                .findFirst();
    }

    @Override
    public boolean test(@NotNull final String other) {
        return getName().equals(other.toLowerCase());
    }

    @Override
    public String toString() {
        return name;
    }
}
