package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.quote.models.CurrencyPairExchangeRatePageResponse;
import org.springframework.data.domain.Pageable;

public interface QuoteService {
    PageResponse<CurrencyPairExchangeRatePageResponse>getCurrencyQuotes(Pageable pageable);
    CurrencyPairExchangeRatePageResponse getCurrencyQuotesByCurrencyPairName(String currencyPairName,
                                                                             Pageable pageable);
    PageResponse<CurrencyPairExchangeRatePageResponse> getCryptoCurrencyQuotes(Pageable pageable);

    PageResponse<CurrencyPairExchangeRatePageResponse> getForexCurrencyQuotes(Pageable pageable);
}
