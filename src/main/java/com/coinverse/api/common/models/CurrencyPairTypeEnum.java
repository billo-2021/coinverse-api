package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum CurrencyPairTypeEnum {
    FOREX("forex", "Forex"),
    CRYPTO("crypto", "Forex");

    private final String code;
    private final String name;

    private CurrencyPairTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }

    public static Optional<CurrencyPairTypeEnum> of(String code) {
        return Stream.of(values())
                .filter(currencyPairTypeEnum ->
                        currencyPairTypeEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
