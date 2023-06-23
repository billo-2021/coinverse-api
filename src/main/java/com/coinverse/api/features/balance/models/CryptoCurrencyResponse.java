package com.coinverse.api.features.balance.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CryptoCurrencyResponse {
    private String code;
    private String name;
    private String symbol;
}
