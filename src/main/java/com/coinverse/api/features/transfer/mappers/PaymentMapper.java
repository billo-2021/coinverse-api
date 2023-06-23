package com.coinverse.api.features.transfer.mappers;

import com.coinverse.api.common.entities.Payment;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    PaymentResponse paymentToPaymentResponse(Payment payment);
}
