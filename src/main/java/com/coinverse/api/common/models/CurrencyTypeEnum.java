package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum CurrencyTypeEnum {
    FIAT("fiat", "Fiat"),
    CRYPTO("crypto", "Crypto");

    private final String code;
    private final String name;

    private CurrencyTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {return code; }
    public String getName() {
        return name;
    }

    public static Optional<CurrencyTypeEnum> of(String code) {
        return Stream.of(values())
                .filter(currencyTypeEnum ->
                        currencyTypeEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
