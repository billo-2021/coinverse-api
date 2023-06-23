package com.coinverse.api.features.lookup.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CurrencyResponse {
    private String name;
    private String code;
    private String symbol;
    private CountryResponse country;
}
