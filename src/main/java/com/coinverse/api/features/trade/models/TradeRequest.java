package com.coinverse.api.features.trade.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TradeRequest {
    private double amount;
    private String amountCurrencyCode;
    private Long quoteId;
    private String action;
}
