package com.coinverse.api.features.trade.services;

import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;

public interface TradeProcessor {
    CurrencyTransactionResponse requestTrade(TradeRequest tradeRequest);
}
