package com.coinverse.api.features.transfer.mappers;

import com.coinverse.api.common.entities.Payment;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    @Mapping(source = "payment.currency.code", target = "currency")
    @Mapping(source = "payment.method.name", target = "method")
    @Mapping(source = "payment.action.name", target = "action")
    @Mapping(source = "payment.status.name", target = "status")
    PaymentResponse paymentToPaymentResponse(Payment payment);
}
