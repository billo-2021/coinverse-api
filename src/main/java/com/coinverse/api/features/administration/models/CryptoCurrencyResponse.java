package com.coinverse.api.features.administration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CryptoCurrencyResponse {
    private String name;
    private String code;
    private String symbol;
    private Long circulatingSupply;
}
