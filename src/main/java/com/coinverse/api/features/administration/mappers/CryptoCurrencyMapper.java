package com.coinverse.api.features.administration.mappers;

import com.coinverse.api.common.entities.CryptoCurrency;
import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.features.administration.models.CryptoCurrencyRequest;
import com.coinverse.api.features.administration.models.CryptoCurrencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CryptoCurrencyMapper {
    Currency cryptoCurrencyRequestToCurrency(CryptoCurrencyRequest cryptoCurrencyRequest);

    @Mapping(source = "currency.name", target = "name")
    @Mapping(source = "currency.code", target = "code")
    @Mapping(source = "currency.symbol", target = "symbol")
    CryptoCurrencyResponse cryptoCurrencyToCryptoCurrencyResponse(CryptoCurrency cryptoCurrency);
}
