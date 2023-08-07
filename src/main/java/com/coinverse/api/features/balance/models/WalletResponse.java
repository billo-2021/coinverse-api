package com.coinverse.api.features.balance.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "address", "currency", "balance"})
public class WalletResponse {
    private Long id;
    private String address;
    private CryptoCurrencyResponse currency;
    private double balance;
}
