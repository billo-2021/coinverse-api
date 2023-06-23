package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum PaymentActionEnum {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw");
    private final String name;

    private PaymentActionEnum(final @NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<PaymentActionEnum> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(paymentActionEnum ->
                        paymentActionEnum.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}
