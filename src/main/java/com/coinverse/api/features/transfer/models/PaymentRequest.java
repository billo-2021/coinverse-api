package com.coinverse.api.features.transfer.models;

import com.coinverse.api.common.models.PaymentActionEnum;
import com.coinverse.api.common.models.PaymentMethodEnum;
import com.coinverse.api.common.validators.EnumValidator;
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
    @EnumValidator(message = "method is required", target = PaymentMethodEnum.class)
    private String method;
    @EnumValidator(message = "action is required", target = PaymentActionEnum.class)
    private String action;
}
