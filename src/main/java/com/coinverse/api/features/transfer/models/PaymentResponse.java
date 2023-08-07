package com.coinverse.api.features.transfer.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "amount", "currency", "exchangeRate",
        "method", "action", "status", "createdAt"
})
public class PaymentResponse {
    private Long id;
    private BigDecimal amount;
    private String currency;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ExchangeRateResponse exchangeRate;
    private String method;
    private String action;
    private String status;
    private OffsetDateTime createdAt;
}
