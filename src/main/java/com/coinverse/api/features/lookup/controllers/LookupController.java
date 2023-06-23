package com.coinverse.api.features.lookup.controllers;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.lookup.models.CountryResponse;
import com.coinverse.api.features.lookup.models.CurrencyResponse;
import com.coinverse.api.features.lookup.services.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(LookupController.PATH)
@RequiredArgsConstructor
public class LookupController {
    private final LookupService lookupService;

    public static final String PATH = "/api/v1/lookup";

    @GetMapping("/countries/all")
    ResponseEntity<PageResponse<CountryResponse>> getAllCountries() {
        final PageResponse<CountryResponse> countryPageResponse = lookupService.getAllCountries();
        return ResponseEntity.ok(countryPageResponse);
    }

    @GetMapping("/currencies/all")
    ResponseEntity<PageResponse<CurrencyResponse>> getAllCurrencies() {
        final PageResponse<CurrencyResponse> currencyPageResponse = lookupService.getAllCurrencies();
        return ResponseEntity.ok(currencyPageResponse);
    }

    @GetMapping("/notification-methods/all")
    ResponseEntity<PageResponse<String>> getNotificationMethods() {
        return ResponseEntity.ok(lookupService.getNotificationMethods());
    }
}
