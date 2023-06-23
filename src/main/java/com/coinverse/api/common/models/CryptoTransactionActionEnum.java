package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum CryptoTransactionActionEnum {
    BUY("buy"),
    SELL("sell");
    private final String name;

    private CryptoTransactionActionEnum(final @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<CryptoTransactionActionEnum> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(cryptoTransactionActionEnum ->
                        cryptoTransactionActionEnum.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
