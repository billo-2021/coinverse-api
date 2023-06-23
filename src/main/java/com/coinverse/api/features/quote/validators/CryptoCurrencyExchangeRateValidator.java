package com.coinverse.api.features.quote.validators;

import com.coinverse.api.common.entities.CryptoCurrencyPair;
import com.coinverse.api.common.exceptions.NotFoundException;
import com.coinverse.api.common.repositories.CryptoCurrencyPairRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CryptoCurrencyExchangeRateValidator {
    private final CryptoCurrencyPairRepository currencyPairRepository;

    public CryptoCurrencyPair validateCurrencyPairName(@NotNull final String currencyPairName) {
        return currencyPairRepository.findByName(currencyPairName)
                .orElseThrow(NotFoundException::new);
    }
}
