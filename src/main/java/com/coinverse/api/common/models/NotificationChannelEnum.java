package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum NotificationChannelEnum {
    PUSH("push", "Push"),
    EMAIL("email", "Email"),
    SMS("sms", "SMS");

    private final String code;
    private final String name;

    private NotificationChannelEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }

    public static Optional<NotificationChannelEnum> of(String code) {
        return Stream.of(values())
                .filter(notificationChannelEnum ->
                        notificationChannelEnum.getCode().equalsIgnoreCase(code)
                ).findFirst();
    }
}
