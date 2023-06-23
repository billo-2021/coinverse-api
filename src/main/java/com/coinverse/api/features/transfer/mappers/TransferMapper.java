package com.coinverse.api.features.transfer.mappers;

import com.coinverse.api.common.models.PaymentActionEnum;
import com.coinverse.api.features.transfer.models.DepositRequest;
import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.WithdrawRequest;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransferMapper {
    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "method", source = "paymentMethod")
    PaymentRequest depositRequestToPaymentRequest(DepositRequest depositRequest);

    @AfterMapping
    default void addDepositPaymentAction(DepositRequest depositRequest, @MappingTarget PaymentRequest paymentRequest) {
        paymentRequest.setAction(PaymentActionEnum.DEPOSIT.getName());
    }

    @BeanMapping(builder = @Builder(disableBuilder = true))
    @Mapping(target = "method", source = "paymentMethod")
    PaymentRequest withdrawRequestToPaymentRequest(WithdrawRequest withdrawRequest);

    @AfterMapping
    default void addWithdrawPaymentAction(WithdrawRequest depositRequest, @MappingTarget PaymentRequest paymentRequest) {
        paymentRequest.setAction(PaymentActionEnum.WITHDRAW.getName());
    }
}
