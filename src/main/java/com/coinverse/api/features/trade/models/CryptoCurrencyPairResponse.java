package com.coinverse.api.features.trade.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CryptoCurrencyPairResponse {
    private String name;
    private CryptoCurrencyResponse baseCurrency;
    private CryptoCurrencyResponse quoteCurrency;
}
