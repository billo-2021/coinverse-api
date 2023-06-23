package com.coinverse.api.features.trade.mappers;

import com.coinverse.api.common.entities.Wallet;
import com.coinverse.api.features.trade.models.WalletResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    WalletResponse walletToWalletResponse(Wallet wallet);
}
