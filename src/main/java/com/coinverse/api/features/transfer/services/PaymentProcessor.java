package com.coinverse.api.features.transfer.services;

import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;

public interface PaymentProcessor {
    PaymentResponse makePayment(PaymentRequest paymentRequest);
}
