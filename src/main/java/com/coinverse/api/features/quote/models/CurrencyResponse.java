package com.coinverse.api.features.quote.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CurrencyResponse {
    private String code;
    private String name;
    private String symbol;
}
