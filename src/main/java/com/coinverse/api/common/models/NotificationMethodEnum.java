package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum NotificationMethodEnum {
    PUSH("push", "Push"),
    EMAIL("email", "Email"),
    SMS("sms", "SMS");

    private final String code;
    private final String name;

    private NotificationMethodEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }

    public static Optional<NotificationMethodEnum> of(String code) {
        return Stream.of(values())
                .filter(notificationMethodEnum ->
                        notificationMethodEnum.getCode().equalsIgnoreCase(code)
                ).findFirst();
    }
}
