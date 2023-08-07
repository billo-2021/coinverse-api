package com.coinverse.api.features.trade.mappers;

import com.coinverse.api.common.entities.CurrencyTransaction;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TradeMapper {
    @Mapping(source = "currencyTransaction.currency.code", target = "currency")
    @Mapping(source = "currencyTransaction.action.name", target = "action")
    @Mapping(source = "currencyTransaction.status.name", target = "status")
    CurrencyTransactionResponse currencyTransactionToCurrencyTransactionResponse(CurrencyTransaction currencyTransaction);
}
