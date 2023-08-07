package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.Country;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CountryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {
    CountryResponse countryToCountryResponse(Country country);

    List<CountryResponse> countriesToCountriesResponse(List<Country> countries);
    default PageResponse<CountryResponse> countryPageToCountryResponsePage(Page<Country> countryPage) {
        final Page<CountryResponse> countryResponsePage = countryPage.map(this::countryToCountryResponse);
        return PageResponse.of(countryResponsePage);
    }
}
