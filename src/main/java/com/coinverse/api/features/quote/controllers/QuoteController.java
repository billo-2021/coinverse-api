package com.coinverse.api.features.quote.controllers;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.quote.models.CryptoCurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.models.CurrencyExchangeRateResponse;
import com.coinverse.api.features.quote.services.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(QuoteController.PATH)
@RequiredArgsConstructor
public class QuoteController {
    public static final String PATH = "/api/v1/quotes";

    private final QuoteService quoteService;

    private final PageRequestValidator pageRequestValidator;

    @GetMapping("/crypto-currencies")
    public ResponseEntity<List<PageResponse<CryptoCurrencyExchangeRateResponse>>> getCryptoCurrencyQuotes(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        final PageRequest pageRequest = pageRequestValidator.validatePageRequest(pageNumber, pageSize);

        final List<PageResponse<CryptoCurrencyExchangeRateResponse>> currenciesExchangeRateResponsePage =
                quoteService.getCryptoCurrencyQuotes(pageRequest);

        return ResponseEntity.ok(currenciesExchangeRateResponsePage);
    }

    @GetMapping("/crypto-currencies/{currencyPairName}")
    public ResponseEntity<PageResponse<CryptoCurrencyExchangeRateResponse>> getCryptoCurrencyQuotesByCurrencyPairName(
            @PathVariable String currencyPairName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        final PageRequest pageRequest = pageRequestValidator.validatePageRequest(pageNumber, pageSize);

        final PageResponse<CryptoCurrencyExchangeRateResponse> currencyExchangeRateResponsePage =
                quoteService.getCryptoCurrencyQuotesByCurrencyPairName(currencyPairName,pageRequest);

        return ResponseEntity.ok(currencyExchangeRateResponsePage);
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<PageResponse<CurrencyExchangeRateResponse>>> getCurrencyQuotes(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        final PageRequest pageRequest = pageRequestValidator.validatePageRequest(pageNumber, pageSize);

        final List<PageResponse<CurrencyExchangeRateResponse>> currenciesExchangeRateResponsePage =
                quoteService.getCurrencyQuotes(pageRequest);

        return ResponseEntity.ok(currenciesExchangeRateResponsePage);
    }

    @GetMapping("/currencies/{currencyPairName}")
    public ResponseEntity<PageResponse<CurrencyExchangeRateResponse>> getCurrencyQuoteByCurrencyPairName(
            @PathVariable String currencyPairName,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        final PageRequest pageRequest = pageRequestValidator.validatePageRequest(pageNumber, pageSize);

        final PageResponse<CurrencyExchangeRateResponse> currencyExchangeRateResponsePage =
                quoteService.getCurrencyQuotesByCurrencyPairName(currencyPairName,pageRequest);

        return ResponseEntity.ok(currencyExchangeRateResponsePage);
    }
}
