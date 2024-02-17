package com.coinverse.api.common.models;

import com.coinverse.api.common.validators.DefaultStringEnumComparator;

import java.util.Optional;
import java.util.stream.Stream;

public enum CurrencyTransactionActionEnum implements DefaultStringEnumComparator {
    BUY("buy", "Buy"),
    SELL("sell", "Sell");

    private final String code;
    private final String name;

    CurrencyTransactionActionEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String getCode() {
        return code;
    }

    public String getName() { return name; }

    public static Optional<CurrencyTransactionActionEnum> of(String code) {
        return Stream.of(values())
                .filter(currencyTransactionActionEnum ->
                        currencyTransactionActionEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }

    @Override
    public String toString() {
        return name;
    }
}
