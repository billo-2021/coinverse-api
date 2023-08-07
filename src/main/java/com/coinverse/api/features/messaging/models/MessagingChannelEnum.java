package com.coinverse.api.features.messaging.models;

import com.coinverse.api.common.validators.DefaultStringEnumComparator;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.stream.Stream;

@Validated
public enum MessagingChannelEnum implements DefaultStringEnumComparator {
    PUSH("push", "Push"),
    EMAIL("email", "Email"),
    SMS("sms", "SMS");

    private final String code;
    private final String name;

    private MessagingChannelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getName() { return name; }

    public static Optional<MessagingChannelEnum> of(String code) {
        return Stream.of(values())
                .filter(messagingChannelEnum -> messagingChannelEnum.test(code))
                .findFirst();
    }

    @Override
    public String toString() {
        return name;
    }
}
