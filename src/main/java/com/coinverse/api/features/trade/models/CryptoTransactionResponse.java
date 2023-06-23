package com.coinverse.api.features.trade.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Builder
public class CryptoTransactionResponse {
    private BigDecimal amount;
    private String amountCurrencyCode;
    private CryptoCurrencyExchangeRateResponse exchangeRate;
    private String action;
    private WalletResponse sourceWallet;
    private WalletResponse destinationWallet;
    private String status;
    private OffsetDateTime createdAt;
}
