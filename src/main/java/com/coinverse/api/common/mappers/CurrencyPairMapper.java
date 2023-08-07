package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.CurrencyPair;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CurrencyPairResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CurrencyPairMapper {
    @Mapping(source = "type.name", target = "type")
    @Mapping(source = "baseCurrency.type.name", target = "baseCurrency.type")
    @Mapping(target = "baseCurrency.countries", ignore = true)
    @Mapping(source = "quoteCurrency.type.name", target = "quoteCurrency.type")
    @Mapping(target = "quoteCurrency.countries", ignore = true)
    CurrencyPairResponse currencyPairToCurrencyPairResponse(CurrencyPair currencyPair);
    List<CurrencyPairResponse> currencyPairsToCurrencyPairsResponse(List<CurrencyPair> currencyPairs);
    default PageResponse<CurrencyPairResponse> currencyPairsPageToCurrencyPairsPageResponse(Page<CurrencyPair> currencyPairPage) {
        final Page<CurrencyPairResponse> currencyPairResponsePage = currencyPairPage.map(this::currencyPairToCurrencyPairResponse);

        return PageResponse.of(currencyPairResponsePage);
    }
}
