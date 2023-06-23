package com.coinverse.api.features.quote.mappers;

import com.coinverse.api.common.entities.CurrencyExchangeRate;
import com.coinverse.api.features.quote.models.CurrencyExchangeRateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyExchangeRateMapper {
    CurrencyExchangeRateResponse currencyExchangeRateToCurrencyExchangeRateResponse(
            CurrencyExchangeRate currencyExchangeRate);
}
