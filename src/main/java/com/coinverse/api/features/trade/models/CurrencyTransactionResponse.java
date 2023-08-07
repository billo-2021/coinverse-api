package com.coinverse.api.features.trade.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "amount", "currency",
        "action", "sourceWallet", "destinationWallet",
        "status", "createdAt"
})
public class CurrencyTransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String currency;
    private String action;
    private WalletResponse sourceWallet;
    private WalletResponse destinationWallet;
    private String status;
    private OffsetDateTime createdAt;
}
