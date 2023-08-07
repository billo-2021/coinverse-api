package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.models.CurrencyPairTypeEnum;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.quote.models.CurrencyPairExchangeRatePageResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {
    private final CurrencyExchangeRateService currencyExchangeRateService;

    @Override
    public PageResponse<CurrencyPairExchangeRatePageResponse> getCurrencyQuotes(Pageable pageable) {
        return currencyExchangeRateService.getExchangeRates(pageable);
    }

    @Override
    public CurrencyPairExchangeRatePageResponse getCurrencyQuotesByCurrencyPairName(String currencyPairName, Pageable pageable) {
        return currencyExchangeRateService.getExchangeRatesByCurrencyPairName(currencyPairName, pageable);
    }

    @Override
    public PageResponse<CurrencyPairExchangeRatePageResponse> getCryptoCurrencyQuotes(Pageable pageable) {
        final String currencyPairTypeCode = CurrencyPairTypeEnum.CRYPTO.getCode();

        return currencyExchangeRateService.getExchangeRateByCurrencyPairTypeCode(currencyPairTypeCode, pageable);
    }

    @Override
    public PageResponse<CurrencyPairExchangeRatePageResponse> getForexCurrencyQuotes(Pageable pageable) {
        final String currencyPairTypeCode = CurrencyPairTypeEnum.FOREX.getCode();

        return currencyExchangeRateService.getExchangeRateByCurrencyPairTypeCode(currencyPairTypeCode, pageable);
    }
}
