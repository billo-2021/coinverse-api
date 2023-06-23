package com.coinverse.api.features.quote.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Builder
public class CurrencyExchangeRateResponse {
    private String currencyPairName;
    private CurrencyResponse baseCurrency;
    private CurrencyResponse quoteCurrency;
    private double bidRate;
    private double askRate;
    private OffsetDateTime createdAt;
    private Integer timeToLive;
    private OffsetDateTime updatedAt;
}
