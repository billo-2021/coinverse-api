package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.*;
import com.coinverse.api.common.services.*;
import com.coinverse.api.common.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LookupServiceImpl implements LookupService {
    private final CountryService countryService;
    private final CurrencyService currencyService;
    private final CryptoCurrencyService cryptoCurrencyService;
    private final CurrencyPairService currencyPairService;
    private final NotificationMethodService notificationMethodService;
    private final PaymentMethodService paymentMethodService;

    @Override
    public List<CountryResponse> findAllCountries() {
        return countryService.findAll();
    }

    @Override
    public PageResponse<CountryResponse> findAllCountries(Pageable pageable) {
        return countryService.findAll(pageable);
    }

    @Override
    public CountryResponse findCountryByCode(String code) {
        return countryService.findByCode(code);
    }

    @Override
    public List<CurrencyResponse> findAllCurrencies() {
        return currencyService.findAll();
    }

    @Override
    public PageResponse<CurrencyResponse> findAllCurrencies(Pageable pageable) {
        return currencyService.findAll(pageable);
    }

    @Override
    public CurrencyResponse findCurrencyByCode(String code) {
        return currencyService.findByCode(code);
    }

    @Override
    public List<CurrencyResponse> findAllCurrenciesByType(String type) {
        return currencyService.findAllByType(type);
    }

    @Override
    public PageResponse<CurrencyResponse> findAllCurrenciesByType(String type, Pageable pageable) {
        return currencyService.findAllByType(type, pageable);
    }

    @Override
    public List<CryptoCurrencyResponse> findAllCryptoCurrencies() {
        return cryptoCurrencyService.findAll();
    }

    @Override
    public PageResponse<CryptoCurrencyResponse> findAllCryptoCurrencies(Pageable pageable) {
        return cryptoCurrencyService.findAll(pageable);
    }

    @Override
    public CryptoCurrencyResponse findCryptoCurrencyByCurrencyCode(String code) {
        return cryptoCurrencyService.findByCurrencyCode(code);
    }

    @Override
    public List<CurrencyPairResponse> findAllCurrencyPairs() {
        return currencyPairService.findAllCurrencyPairs();
    }

    @Override
    public PageResponse<CurrencyPairResponse> findAllCurrencyPairs(Pageable pageable) {
        return currencyPairService.findAllCurrencyPairs(pageable);
    }

    @Override
    public CurrencyPairResponse findCurrencyPairByName(String name) {
        return currencyPairService.findByName(name);
    }

    @Override
    public List<CurrencyPairResponse> findAllCurrencyPairsByType(String type) {
        return currencyPairService.findAllByType(type);
    }

    @Override
    public PageResponse<CurrencyPairResponse> findAllCurrencyPairsByType(String type, Pageable pageable) {
        return currencyPairService.findAllByType(type, pageable);
    }

    @Override
    public List<NotificationMethodResponse> findAllNotificationMethods() {
        return notificationMethodService.findAll();
    }

    @Override
    public PageResponse<NotificationMethodResponse> findAllNotificationMethods(Pageable pageable) {
        return notificationMethodService.findAll(pageable);
    }

    @Override
    public List<PaymentMethodResponse> findAllPaymentMethods() {
        return paymentMethodService.findAll();
    }

    @Override
    public PageResponse<PaymentMethodResponse> findAllPaymentMethods(Pageable pageable) {
        return paymentMethodService.findAll(pageable);
    }
}
