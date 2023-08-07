package com.coinverse.api.features.lookup.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LookupService {
    List<CountryResponse> findAllCountries();

    PageResponse<CountryResponse> findAllCountries(Pageable pageable);
    CountryResponse findCountryByCode(String code);

    List<CurrencyResponse> findAllCurrencies();

    PageResponse<CurrencyResponse> findAllCurrencies(Pageable pageable);

    CurrencyResponse findCurrencyByCode(String code);

    List<CurrencyResponse> findAllCurrenciesByType(String type);

    PageResponse<CurrencyResponse> findAllCurrenciesByType(String type, Pageable pageable);

    List<CryptoCurrencyResponse> findAllCryptoCurrencies();
    PageResponse<CryptoCurrencyResponse> findAllCryptoCurrencies(Pageable pageable);
    CryptoCurrencyResponse findCryptoCurrencyByCurrencyCode(String code);

    List<CurrencyPairResponse> findAllCurrencyPairs();
    PageResponse<CurrencyPairResponse> findAllCurrencyPairs(Pageable pageable);
    CurrencyPairResponse findCurrencyPairByName(String name);

    List<CurrencyPairResponse> findAllCurrencyPairsByType(String type);
    PageResponse<CurrencyPairResponse> findAllCurrencyPairsByType(String type, Pageable pageable);
    List<NotificationMethodResponse> findAllNotificationMethods();
    PageResponse<NotificationMethodResponse> findAllNotificationMethods(Pageable pageable);

    List<PaymentMethodResponse> findAllPaymentMethods();
    PageResponse<PaymentMethodResponse> findAllPaymentMethods(Pageable pageable);
}
