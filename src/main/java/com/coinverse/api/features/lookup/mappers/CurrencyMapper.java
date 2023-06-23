package com.coinverse.api.features.lookup.mappers;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.features.lookup.models.CurrencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyMapper {
    CurrencyResponse currencyToCurrencyResponse(Currency currency);
}
