package com.coinverse.api.features.trade.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
public class WalletResponse {
    private CryptoCurrencyResponse currency;
    private String address;
    private BigDecimal balance;
}
