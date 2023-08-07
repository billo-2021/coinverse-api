package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.Currency;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CurrencyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyMapper {
    @Mapping(source = "type.name", target = "type")
    CurrencyResponse currencyToCurrencyResponse(Currency currency);
    List<CurrencyResponse> currenciesToCurrenciesResponse(List<Currency> currencies);

    default PageResponse<CurrencyResponse> currencyPageToCurrencyPageResponse(Page<Currency> currencyPage) {
        final Page<CurrencyResponse> currencyResponsePage = currencyPage.map(this::currencyToCurrencyResponse);

        return PageResponse.of(currencyResponsePage);
    }
}
