package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import org.springframework.data.domain.Pageable;

public interface TradeService {
    CurrencyTransactionResponse requestTrade(TradeRequest tradeRequest);
    PageResponse<CurrencyTransactionResponse> getTrades(Pageable pageable);
    CurrencyTransactionResponse getTradeById(Long id);
}
