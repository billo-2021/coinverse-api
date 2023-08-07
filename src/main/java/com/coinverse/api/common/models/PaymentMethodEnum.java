package com.coinverse.api.common.models;

import com.coinverse.api.common.validators.DefaultStringEnumComparator;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum PaymentMethodEnum implements DefaultStringEnumComparator {
    DEBIT_OR_CREDIT_CARD("debit_or_credit_card", "Debit or Credit Card"),
    BANK_TRANSFER("bank_transfer", "Bank Transfer");

    private final String code;
    private final String name;

    private PaymentMethodEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getName() { return name; }

    public static Optional<PaymentMethodEnum> of(final @NotNull String name) {
        return Stream.of(values())
                .filter(paymentMethodEnum ->
                        paymentMethodEnum.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public String toString() {
        return name;
    }
}
