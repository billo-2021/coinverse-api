package com.coinverse.api.features.quote.validators;

import com.coinverse.api.common.entities.CurrencyPair;
import com.coinverse.api.common.exceptions.NotFoundException;
import com.coinverse.api.common.repositories.CurrencyPairRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component@RequiredArgsConstructor
public class CurrencyExchangeRateValidator {
    private final CurrencyPairRepository currencyPairRepository;

    public CurrencyPair validateCurrencyPairName(@NotNull final String currencyPairName) {
        return currencyPairRepository.findByName(currencyPairName)
                .orElseThrow(NotFoundException::new);
    }
}
