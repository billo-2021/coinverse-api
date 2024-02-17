package com.coinverse.api.common.models;

import com.coinverse.api.common.validators.DefaultStringEnumComparator;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum PaymentActionEnum  implements DefaultStringEnumComparator {
    DEPOSIT("deposit", "Deposit"),
    WITHDRAW("withdraw", "Withdraw");

    private final String code;
    private final String name;

    private PaymentActionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }

    public String getName() {
        return name;
    }

    public static Optional<PaymentActionEnum> of(final @NotNull String code) {
        return Stream.of(values())
                .filter(paymentActionEnum ->
                        paymentActionEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    @Override
    public String toString() {
        return name;
    }
}
