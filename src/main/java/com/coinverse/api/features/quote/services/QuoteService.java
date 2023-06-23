package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.quote.models.CryptoCurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.models.CurrencyExchangeRateResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface QuoteService {
    List<PageResponse<CryptoCurrencyExchangeRateResponse>> getCryptoCurrencyQuotes(@NotNull final PageRequest pageRequest);
    PageResponse<CryptoCurrencyExchangeRateResponse> getCryptoCurrencyQuotesByCurrencyPairName(
            @NotNull final String currencyPaiName,
            @NotNull final PageRequest pageRequest);

    List<PageResponse<CurrencyExchangeRateResponse>> getCurrencyQuotes(@NotNull final PageRequest pageRequest);
    PageResponse<CurrencyExchangeRateResponse> getCurrencyQuotesByCurrencyPairName(@NotNull final String currencyPairName,
                                                                                   @NotNull final PageRequest pageRequest);
}
