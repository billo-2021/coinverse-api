package com.coinverse.api.features.transfer.models;

import com.coinverse.api.common.models.PaymentMethodEnum;
import com.coinverse.api.common.validators.EnumValidator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class WithdrawRequest {
    @DecimalMin(value = "1.00", message = "amount must be equal or greater than 1.00")
    @DecimalMax(value = "10000000", message = "amount must be equal or less than 10000000")
    private double amount;

    @NotBlank(message = "amountCurrencyCode is required")
    private String amountCurrencyCode;

    private String fromCurrencyCode;

    private String toCurrencyCode;

    private String currencyPairName;

    private Long quoteId;

    @EnumValidator(message = "paymentMethod is required", target = PaymentMethodEnum.class)
    private String paymentMethod;
}
