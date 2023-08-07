package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.entities.CryptoCurrency;
import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.repositories.CryptoCurrencyRepository;
import com.coinverse.api.common.repositories.CurrencyRepository;
import com.coinverse.api.features.administration.mappers.CryptoCurrencyMapper;
import com.coinverse.api.features.administration.models.CryptoCurrencyRequest;

import com.coinverse.api.features.administration.models.CryptoCurrencyResponse;
import com.coinverse.api.features.administration.models.CryptoCurrencyUpdateRequest;
import com.coinverse.api.features.administration.validators.CurrencyAdministrationValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurrencyAdministrationService {
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyAdministrationValidator currencyAdministrationValidator;

    private final CryptoCurrencyMapper currencyMapper;

    @Transactional
    public CryptoCurrencyResponse addCryptoCurrency(CryptoCurrencyRequest cryptoCurrencyRequest) {
        final CryptoCurrency cryptoCurrency = currencyAdministrationValidator.validateCryptoCurrencyRequest(cryptoCurrencyRequest);
        final Currency currency = cryptoCurrency.getCurrency();

        currencyRepository.saveAndFlush(currency);

        CryptoCurrency savedCryptoCurrency = cryptoCurrencyRepository.saveAndFlush(cryptoCurrency);
        return currencyMapper.cryptoCurrencyToCryptoCurrencyResponse(savedCryptoCurrency);
    }

    @Transactional
    public CryptoCurrencyResponse updateCryptoCurrency(String currencyCode, CryptoCurrencyUpdateRequest cryptoCurrencyUpdateRequest) {
        final CryptoCurrency cryptoCurrency = currencyAdministrationValidator.validateCryptoCurrencyUpdateRequest(currencyCode);
        final Currency currency = cryptoCurrency.getCurrency();

        currency.setName(cryptoCurrencyUpdateRequest.getName());
        currency.setSymbol(cryptoCurrencyUpdateRequest.getSymbol());

        currencyRepository.saveAndFlush(currency);

        cryptoCurrency.setCirculatingSupply(cryptoCurrencyUpdateRequest.getCirculatingSupply());
        final CryptoCurrency savedCryptoCurrency = cryptoCurrencyRepository.saveAndFlush(cryptoCurrency);

         return currencyMapper.cryptoCurrencyToCryptoCurrencyResponse(savedCryptoCurrency);
    }
}
