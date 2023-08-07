package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.CryptoCurrency;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CryptoCurrencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CryptoCurrencyMapper {
    @Mapping(source = "currency.name", target = "name")
    @Mapping(source = "currency.code", target = "code")
    @Mapping(source = "currency.symbol", target = "symbol")
    CryptoCurrencyResponse cryptoCurrencyToCryptoCurrencyResponse(CryptoCurrency cryptoCurrency);
    List<CryptoCurrencyResponse> cryptoCurrenciesToCryptoCurrenciesResponse(List<CryptoCurrency> cryptoCurrencies);

    default PageResponse<CryptoCurrencyResponse> cryptoCurrencyPageToCryptoCurrencyPageResponse(Page<CryptoCurrency> currencyPage) {
        final Page<CryptoCurrencyResponse> cryptoCurrencyResponsePage = currencyPage.map(this::cryptoCurrencyToCryptoCurrencyResponse);

        return PageResponse.of(cryptoCurrencyResponsePage);
    }
}
