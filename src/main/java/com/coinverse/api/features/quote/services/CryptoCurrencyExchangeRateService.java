package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.entities.CryptoCurrencyExchangeRate;
import com.coinverse.api.common.entities.CryptoCurrencyPair;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.CryptoCurrencyExchangeRateRepository;
import com.coinverse.api.common.repositories.CryptoCurrencyPairRepository;
import com.coinverse.api.features.quote.mappers.CryptoCurrencyExchangeRateMapper;
import com.coinverse.api.features.quote.models.CryptoCurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.validators.CryptoCurrencyExchangeRateValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoCurrencyExchangeRateService {
    private final CryptoCurrencyPairRepository currencyPairRepository;
    private final CryptoCurrencyExchangeRateRepository currencyExchangeRateRepository;

    private final CryptoCurrencyExchangeRateValidator exchangeRateValidator;
    private final CryptoCurrencyExchangeRateMapper exchangeRateMapper;

    public List<PageResponse<CryptoCurrencyExchangeRateResponse>> getExchangeRates(
            @NotNull final PageRequest pageRequest) {
        final List<CryptoCurrencyPair> currencyPairs = currencyPairRepository.findAll();

        return currencyPairs
                .stream().map(cryptoCurrencyPair -> {
                    final Page<CryptoCurrencyExchangeRate> currencyExchangeRatePage = currencyExchangeRateRepository
                            .findByCurrencyPairId(cryptoCurrencyPair.getId(), pageRequest);

                    final Page<CryptoCurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                            .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

                    return PageResponse.of(currencyExchangeRateResponsePage);
                }).toList();
    }

    public PageResponse<CryptoCurrencyExchangeRateResponse> getExchangeRatesByCurrencyPairName(
            @NotNull final String currencyPairName, PageRequest pageRequest) {
        final CryptoCurrencyPair currencyPair = exchangeRateValidator.validateCurrencyPairName(currencyPairName);

        final Page<CryptoCurrencyExchangeRate> currencyExchangeRatePage = currencyExchangeRateRepository
                .findByCurrencyPairId(currencyPair.getId(), pageRequest);

        final Page<CryptoCurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

        return PageResponse.of(currencyExchangeRateResponsePage);
    }
}
