package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.entities.Country;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.CountryRepository;
import com.coinverse.api.features.lookup.mappers.CountryMapper;
import com.coinverse.api.features.lookup.models.CountryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public PageResponse<CountryResponse> getAllCountries() {
        final Page<Country> countryPage = countryRepository.findAll(PageRequest.of(0, Integer.MAX_VALUE));
        final Page<CountryResponse> countryResponsePage = countryPage.map(countryMapper::countryToCountryResponse);

        return PageResponse.of(countryResponsePage);
    }
}
