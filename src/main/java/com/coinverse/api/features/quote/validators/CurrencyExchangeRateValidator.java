package com.coinverse.api.features.quote.validators;

import com.coinverse.api.common.entities.CurrencyPair;
import com.coinverse.api.common.entities.CurrencyPairType;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.NotFoundException;
import com.coinverse.api.common.repositories.CurrencyPairRepository;
import com.coinverse.api.common.repositories.CurrencyPairTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component@RequiredArgsConstructor
public class CurrencyExchangeRateValidator {
    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyPairTypeRepository currencyPairTypeRepository;

    public CurrencyPair validateCurrencyPairName(String currencyPairName) {
        return currencyPairRepository.findByNameIgnoreCase(currencyPairName)
                .orElseThrow(NotFoundException::new);
    }

    public CurrencyPairType validateCurrencyPairTypeCode(String currencyTypeCode) {
        return currencyPairTypeRepository.findByCodeIgnoreCase(currencyTypeCode).
                orElseThrow(InvalidRequestException::new);
    }
}
