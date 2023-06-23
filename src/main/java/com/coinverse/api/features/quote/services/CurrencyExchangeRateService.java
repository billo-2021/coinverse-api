package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.entities.CurrencyExchangeRate;
import com.coinverse.api.common.entities.CurrencyPair;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.CurrencyExchangeRateRepository;
import com.coinverse.api.common.repositories.CurrencyPairRepository;
import com.coinverse.api.features.quote.mappers.CurrencyExchangeRateMapper;
import com.coinverse.api.features.quote.models.CurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.validators.CurrencyExchangeRateValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeRateService {
    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;

    private final CurrencyExchangeRateValidator exchangeRateValidator;
    private final CurrencyExchangeRateMapper exchangeRateMapper;

    public List<PageResponse<CurrencyExchangeRateResponse>> getExchangeRates(
            @NotNull final PageRequest pageRequest) {
        final List<CurrencyPair> currencyPairs = currencyPairRepository.findAll();

        return currencyPairs
                .stream().map(cryptoCurrencyPair -> {
                    final Page<CurrencyExchangeRate > currencyExchangeRatePage = currencyExchangeRateRepository
                            .findByCurrencyPairId(cryptoCurrencyPair.getId(), pageRequest);

                    final Page<CurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                            .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

                    return PageResponse.of(currencyExchangeRateResponsePage);
                }).toList();
    }

    public PageResponse<CurrencyExchangeRateResponse> getExchangeRatesByCurrencyPairName(
            @NotNull final String currencyPairName, PageRequest pageRequest) {
        final CurrencyPair currencyPair = exchangeRateValidator.validateCurrencyPairName(currencyPairName);

        final Page<CurrencyExchangeRate> currencyExchangeRatePage = currencyExchangeRateRepository
                .findByCurrencyPairId(currencyPair.getId(), pageRequest);

        final Page<CurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

        return PageResponse.of(currencyExchangeRateResponsePage);
    }
}
