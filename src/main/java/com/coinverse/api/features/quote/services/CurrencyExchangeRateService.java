package com.coinverse.api.features.quote.services;

import com.coinverse.api.common.entities.CurrencyExchangeRate;
import com.coinverse.api.common.entities.CurrencyPair;
import com.coinverse.api.common.entities.CurrencyPairType;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.CurrencyExchangeRateRepository;
import com.coinverse.api.common.repositories.CurrencyPairRepository;
import com.coinverse.api.features.quote.mappers.CurrencyExchangeRateMapper;
import com.coinverse.api.features.quote.models.CurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.models.CurrencyPairExchangeRatePageResponse;
import com.coinverse.api.features.quote.validators.CurrencyExchangeRateValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeRateService {
    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;

    private final CurrencyExchangeRateValidator exchangeRateValidator;
    private final CurrencyExchangeRateMapper exchangeRateMapper;

    public PageResponse<CurrencyPairExchangeRatePageResponse> getExchangeRates(
            Pageable pageable) {

        final Page<CurrencyPair> currencyPairPage = currencyPairRepository.findAll(pageable);

        final Page<CurrencyPairExchangeRatePageResponse> currencyPairExchangeRatePageResponse =
                currencyPairPage.map((currencyPair) -> {
                    final Page<CurrencyExchangeRate > currencyExchangeRatePage = currencyExchangeRateRepository
                            .findByCurrencyPairId(currencyPair.getId(), pageable);

                    final Page<CurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                            .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

                    return CurrencyPairExchangeRatePageResponse.of(currencyExchangeRateResponsePage,
                            currencyPair.getName(), currencyPair.getType().getName());
                });

        return PageResponse.of(currencyPairExchangeRatePageResponse);
    }

    public PageResponse<CurrencyPairExchangeRatePageResponse> getExchangeRateByCurrencyPairTypeCode(
            String code,
            Pageable pageable) {
        final CurrencyPairType currencyPairType = exchangeRateValidator.validateCurrencyPairTypeCode(code);

        final Page<CurrencyPair> currencyPairs = currencyPairRepository.findByTypeId(currencyPairType.getId(), PageRequest.of(0, Integer.MAX_VALUE));

        final Page<CurrencyPairExchangeRatePageResponse> currencyPairExchangeRatePageResponse = currencyPairs
                .map((currencyPair) ->  {
                    final Page<CurrencyExchangeRate> currencyExchangeRatePage = currencyExchangeRateRepository
                            .findByCurrencyPairId(currencyPair.getId(), pageable);

                    final Page<CurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                            .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

                    return CurrencyPairExchangeRatePageResponse.of(currencyExchangeRateResponsePage,
                            currencyPair.getName(), currencyPair.getType().getCode());
                });

        return PageResponse.of(currencyPairExchangeRatePageResponse);
    }

    public CurrencyPairExchangeRatePageResponse getExchangeRatesByCurrencyPairName(
            String currencyPairName, Pageable pageable) {
        final CurrencyPair currencyPair = exchangeRateValidator.validateCurrencyPairName(currencyPairName);

        final Page<CurrencyExchangeRate> currencyExchangeRatePage = currencyExchangeRateRepository
                .findByCurrencyPairId(currencyPair.getId(), pageable);

        final Page<CurrencyExchangeRateResponse> currencyExchangeRateResponsePage = currencyExchangeRatePage
                .map(exchangeRateMapper::currencyExchangeRateToCurrencyExchangeRateResponse);

        return CurrencyPairExchangeRatePageResponse.of(currencyExchangeRateResponsePage,
                currencyPair.getName(), currencyPair.getType().getName());
    }
}
