package com.coinverse.api.features.trade.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"name", "baseCurrency", "quoteCurrency"})
public class CurrencyPairResponse {
    private String name;
    private CurrencyResponse baseCurrency;
    private CurrencyResponse quoteCurrency;
}
