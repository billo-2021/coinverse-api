package com.coinverse.api.features.transfer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PaymentRequest {
    private double amount;
    private String amountCurrencyCode;
    private String currencyPairName;
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private Long quoteId;
    private String method;
    private String action;
}
