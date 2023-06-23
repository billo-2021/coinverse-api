package com.coinverse.api.features.quote.mappers;

import com.coinverse.api.common.entities.CryptoCurrencyExchangeRate;
import com.coinverse.api.features.quote.models.CryptoCurrencyExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CryptoCurrencyExchangeRateMapper {
    CryptoCurrencyExchangeRateResponse currencyExchangeRateToCurrencyExchangeRateResponse(
            CryptoCurrencyExchangeRate currencyExchangeRate);
}
