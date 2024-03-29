package com.coinverse.api.features.quote.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"code", "name", "symbol"})
public class CurrencyResponse {
    private String code;
    private String name;
    private String symbol;
}
