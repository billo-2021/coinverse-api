package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.lookup.models.CountryResponse;
import com.coinverse.api.features.lookup.models.CurrencyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {
    private final CountryService countryService;
    private final CurrencyService currencyService;
    private final UserPreferenceService userPreferenceService;

    @Override
    public PageResponse<CountryResponse> getAllCountries() {
        return countryService.getAllCountries();
    }

    @Override
    public PageResponse<CurrencyResponse> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

    @Override
    public PageResponse<String> getNotificationMethods() {
        return userPreferenceService.getNotificationMethods();
    }
}
