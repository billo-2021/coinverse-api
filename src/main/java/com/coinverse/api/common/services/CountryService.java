package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.Country;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.CountryMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.CountryResponse;
import com.coinverse.api.common.repositories.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public List<CountryResponse> findAll() {
        final List<Country> countries = countryRepository.findAll();

        return countryMapper.countriesToCountriesResponse(countries);
    }

    public PageResponse<CountryResponse> findAll(Pageable pageable) {
        final Page<Country> countryPage = countryRepository.findAll(pageable);

        return countryMapper.countryPageToCountryResponsePage(countryPage);
    }

    public CountryResponse findByCode(String code) {
        final Country country = countryRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new InvalidRequestException("Invalid code '" + code + "'"));

        return countryMapper.countryToCountryResponse(country);
    }
}
