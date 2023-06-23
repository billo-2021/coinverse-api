package com.coinverse.api.features.balance.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class WalletResponse {
    private Long id;
    private CryptoCurrencyResponse currency;
    private String address;
    private double balance;
}
