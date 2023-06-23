package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum CryptoTransactionStatusEnum {
    CREATED("created"),
    SUCCEEDED("succeeded"),
    FAILED("failed");
    private final String name;

    private CryptoTransactionStatusEnum(final @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<CryptoTransactionStatusEnum> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(cryptoTransactionActionEnum ->
                        cryptoTransactionActionEnum.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
