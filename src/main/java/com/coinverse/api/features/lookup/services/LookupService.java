package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.lookup.models.CountryResponse;
import com.coinverse.api.features.lookup.models.CurrencyResponse;

public interface LookupService {
    PageResponse<CountryResponse> getAllCountries();
    PageResponse<CurrencyResponse> getAllCurrencies();

    PageResponse<String> getNotificationMethods();
}
