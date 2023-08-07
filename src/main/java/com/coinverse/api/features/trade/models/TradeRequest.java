package com.coinverse.api.features.trade.models;

import com.coinverse.api.common.models.CurrencyTransactionActionEnum;
import com.coinverse.api.common.validators.EnumValidator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TradeRequest {
    @DecimalMin(value = "1.00", message = "amount must be equal or greater than 1.00")
    @DecimalMax(value = "10000000", message = "amount must be equal or less than 10000000")
    private double amount;

    @NotBlank(message = "amountCurrencyCode is required")
    private String amountCurrencyCode;

    @NotNull(message = "quoteId is required")
    private Long quoteId;

    @EnumValidator(message = "action is required", target = CurrencyTransactionActionEnum.class)
    private String action;
}
