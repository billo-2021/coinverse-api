package com.coinverse.api.features.administration.validators;

import com.coinverse.api.common.entities.CryptoCurrency;
import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.entities.CurrencyType;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.CurrencyTypeEnum;
import com.coinverse.api.common.repositories.CryptoCurrencyRepository;
import com.coinverse.api.common.repositories.CurrencyRepository;
import com.coinverse.api.common.repositories.CurrencyTypeRepository;
import com.coinverse.api.features.administration.mappers.CryptoCurrencyMapper;
import com.coinverse.api.features.administration.models.CryptoCurrencyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CurrencyAdministrationValidator {
    private final CurrencyTypeRepository currencyTypeRepository;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CurrencyRepository currencyRepository;
    private final CryptoCurrencyMapper cryptoCurrencyMapper;

    public CryptoCurrency validateCryptoCurrencyRequest(CryptoCurrencyRequest cryptoCurrencyRequest) {
        final CurrencyTypeEnum currencyTypeEnum = CurrencyTypeEnum.CRYPTO;

        final CurrencyType currencyType  = currencyTypeRepository
                .findByCodeIgnoreCase(currencyTypeEnum.getName())
                .orElseThrow(() -> new MappingException("Currency name '" + currencyTypeEnum.getName() +
                "' is invalid"));

        final Optional<Currency> existingCurrency = currencyRepository.findByCodeIgnoreCase(cryptoCurrencyRequest.getCode());
        existingCurrency.ifPresent((currency) -> {
            throw new ValidationException("Currency with code '" + currency.getCode() + "' already exists", "code");
        });

        final Currency currency = cryptoCurrencyMapper.cryptoCurrencyRequestToCurrency(cryptoCurrencyRequest);
        currency.setType(currencyType);

        return CryptoCurrency
                .builder()
                .currency(currency)
                .circulatingSupply(cryptoCurrencyRequest.getCirculatingSupply())
                .build();
    }

    public CryptoCurrency validateCryptoCurrencyUpdateRequest(String currencyCode) {
        return cryptoCurrencyRepository.findByCurrencyCodeIgnoreCase(currencyCode)
                .orElseThrow(() -> new ValidationException("Crypto currency with code '" + currencyCode + "' does not exist"));
    }
}
