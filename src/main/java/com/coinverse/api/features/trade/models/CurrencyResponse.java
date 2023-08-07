package com.coinverse.api.features.trade.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"name", "code", "symbol"})
public class CurrencyResponse {
    private String name;
    private String code;
    private String symbol;
}
