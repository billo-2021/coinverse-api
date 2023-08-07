package com.coinverse.api.features.lookup.controllers;

import com.coinverse.api.common.config.routes.LookupRoutes;
import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.*;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.lookup.services.LookupService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(LookupRoutes.PATH)
@Validated
@RequiredArgsConstructor
public class LookupController {
    private final LookupService lookupService;

    @GetMapping(LookupRoutes.All_COUNTRIES)
    List<CountryResponse> getAllCountries() {
        return lookupService.findAllCountries();
    }

    @GetMapping(LookupRoutes.COUNTRIES)
    PageResponse<CountryResponse> getCountries(
            @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return lookupService.findAllCountries(PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping(LookupRoutes.ALL_CURRENCIES)
    List<CurrencyResponse> getAllCurrencies(@RequestParam(value = "type", required = false) String type) {

        if (type == null) {
            return lookupService.findAllCurrencies();
        }

        return lookupService.findAllCurrenciesByType(type);
    }

    @GetMapping(value = LookupRoutes.CURRENCIES)
    PageResponse<CurrencyResponse> getCurrencies(@RequestParam(value = "type", required = false) String type,
                                                 @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                 @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                 @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                 @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        if (type == null) {
            return lookupService.findAllCurrencies(PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
        }

        return lookupService.findAllCurrenciesByType(type, PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping(LookupRoutes.ALL_CRYPTO)
    List<CryptoCurrencyResponse> getAllCryptoCurrencies() {
        return lookupService.findAllCryptoCurrencies();
    }

    @GetMapping(value = LookupRoutes.CRYPTO)
    PageResponse<CryptoCurrencyResponse> getCryptoCurrencies(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return lookupService.findAllCryptoCurrencies(PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping(LookupRoutes.CRYPTO_BY_CURRENCY_CODE)
    CryptoCurrencyResponse getCryptoCurrencyByCode(@PathVariable String currencyCode) {
        return lookupService.findCryptoCurrencyByCurrencyCode(currencyCode);
    }

    @GetMapping(LookupRoutes.ALL_CURRENCY_PAIRS)
    List<CurrencyPairResponse> getAllCurrencyPairs() {
        return lookupService.findAllCurrencyPairs();
    }

    @GetMapping(LookupRoutes.CURRENCY_PAIRS)
    PageResponse<CurrencyPairResponse> getCurrencyPairs(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return lookupService.findAllCurrencyPairs(PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping(LookupRoutes.ALL_NOTIFICATION_METHODS)
    List<NotificationMethodResponse> getNotificationMethods() {
        return lookupService.findAllNotificationMethods();
    }

    @GetMapping(LookupRoutes.NOTIFICATION_METHODS)
    PageResponse<NotificationMethodResponse> getAllNotificationMethods(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                                       @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                                       @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                       @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return lookupService.findAllNotificationMethods(PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
    }

    @GetMapping(LookupRoutes.ALL_PAYMENT_METHODS)
    List<PaymentMethodResponse> getAllPaymentMethods() {
        return lookupService.findAllPaymentMethods();
    }
    @GetMapping(LookupRoutes.PAYMENT_METHODS)
    PageResponse<PaymentMethodResponse> getPaymentMethods(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                             @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        return lookupService.findAllPaymentMethods(PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection));
    }
}
