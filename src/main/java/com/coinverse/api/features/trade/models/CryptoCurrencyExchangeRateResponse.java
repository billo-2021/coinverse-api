package com.coinverse.api.features.trade.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
public class CryptoCurrencyExchangeRateResponse {
    private CryptoCurrencyPairResponse currencyPair;
    private BigDecimal bidRate;
    private BigDecimal askRate;
}
