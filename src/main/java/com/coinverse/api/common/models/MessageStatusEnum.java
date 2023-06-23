package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

public enum MessageStatusEnum {
    CREATED("created"),
    READ("read"),
    DELETED("deleted");

    private final String status;

    private MessageStatusEnum(final @NotNull String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
