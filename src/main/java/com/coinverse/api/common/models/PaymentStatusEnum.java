package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum PaymentStatusEnum {
    CREATED("created", "Created"),
    SUCCEEDED("succeeded", "Succeeded"),
    FAILED("failed", "Failed");

    private final String code;
    private final String name;

    private PaymentStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }

    public static Optional<PaymentStatusEnum> of(String code) {
        return Stream.of(values())
                .filter(paymentStatusEnum ->
                        paymentStatusEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
