package com.coinverse.api.features.lookup.mappers;

import com.coinverse.api.common.entities.Country;
import com.coinverse.api.features.lookup.models.CountryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper {
    public CountryResponse countryToCountryResponse(Country country);
}
