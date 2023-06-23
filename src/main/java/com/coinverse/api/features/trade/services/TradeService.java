package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.trade.models.CryptoTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.models.WalletResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;

public interface TradeService {
    CryptoTransactionResponse requestTrade(@NotNull final TradeRequest tradeRequest);
    PageResponse<CryptoTransactionResponse> getTrades(@NotNull final PageRequest pageRequest);
    CryptoTransactionResponse getTradeById(@NotNull final Long id);
    PageResponse<WalletResponse> getWallets(@NotNull final PageRequest pageRequest);
    PageResponse<WalletResponse> getAllWallets();
    WalletResponse getWalletById(@NotNull final Long id);
}
