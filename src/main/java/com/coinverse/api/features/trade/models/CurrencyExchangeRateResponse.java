package com.coinverse.api.features.trade.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"currencyPair", "bidRate", "askRate"})
public class CurrencyExchangeRateResponse {
    private CurrencyPairResponse currencyPair;
    private BigDecimal bidRate;
    private BigDecimal askRate;
}
