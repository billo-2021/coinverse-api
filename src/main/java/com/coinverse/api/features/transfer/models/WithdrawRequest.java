package com.coinverse.api.features.transfer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class WithdrawRequest {
    private double amount;
    private String currencyCode;
    private String paymentMethod;
}
