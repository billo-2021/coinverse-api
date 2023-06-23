package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.quote.models.CryptoCurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.models.CurrencyExchangeRateResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final CryptoCurrencyExchangeRateService cryptoExchangeRateService;
    private final CurrencyExchangeRateService currencyExchangeRateService;

    @Override
    public List<PageResponse<CryptoCurrencyExchangeRateResponse>> getCryptoCurrencyQuotes(
            @NotNull final PageRequest pageRequest) {
        return cryptoExchangeRateService.getExchangeRates(pageRequest);
    }

    @Override
    public PageResponse<CryptoCurrencyExchangeRateResponse> getCryptoCurrencyQuotesByCurrencyPairName(
            @NotNull final String currencyPaiName,
            @NotNull final PageRequest pageRequest) {
        return cryptoExchangeRateService.getExchangeRatesByCurrencyPairName(currencyPaiName, pageRequest);
    }

    @Override
    public List<PageResponse<CurrencyExchangeRateResponse>> getCurrencyQuotes(
            @NotNull final PageRequest pageRequest) {
        return currencyExchangeRateService.getExchangeRates(pageRequest);
    }

    @Override
    public PageResponse<CurrencyExchangeRateResponse> getCurrencyQuotesByCurrencyPairName(
            @NotNull final String currencyPairName,
            @NotNull final PageRequest pageRequest) {
        return currencyExchangeRateService.getExchangeRatesByCurrencyPairName(currencyPairName, pageRequest);
    }
}
