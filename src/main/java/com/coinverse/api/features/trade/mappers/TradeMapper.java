package com.coinverse.api.features.trade.mappers;

import com.coinverse.api.common.entities.CryptoTransaction;
import com.coinverse.api.features.trade.models.CryptoTransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TradeMapper {
    @Mapping(source = "cryptoTransaction.action.name", target = "action")
    @Mapping(source = "cryptoTransaction.status.name", target = "status")
    CryptoTransactionResponse cryptoTransactionToCryptoTransactionResponse(CryptoTransaction cryptoTransaction);
}
