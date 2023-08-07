package com.coinverse.api.common.models;

public enum MessageStatusEnum {
    CREATED("created", "Created"),
    READ("read", "Read"),
    DELETED("deleted", "Read");

    private final String code;
    private final String name;

    private MessageStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }
}
