package com.coinverse.api.features.quote.controllers;

import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.quote.models.CurrencyPairExchangeRatePageResponse;
import com.coinverse.api.features.quote.services.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(QuoteController.PATH)
@RequiredArgsConstructor
public class QuoteController {
    public static final String PATH = "/api/v1/quotes";
    private final QuoteService quoteService;

    @GetMapping
    public PageResponse<CurrencyPairExchangeRatePageResponse> getCurrencyQuotes(
            @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {

        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);

        return quoteService.getCurrencyQuotes(pageable);
    }

    @GetMapping("/currency-pair")

    public CurrencyPairExchangeRatePageResponse getCurrencyQuotesByCurrencyPairName(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection
    ) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);

        return quoteService.getCurrencyQuotesByCurrencyPairName(name, pageable);
    }
    @GetMapping("/crypto")
    public PageResponse<CurrencyPairExchangeRatePageResponse> getCryptoCurrencyQuotes(
            @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {

        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);

        return quoteService.getCryptoCurrencyQuotes(pageable);
    }

    @GetMapping("/forex")
    public PageResponse<CurrencyPairExchangeRatePageResponse> getForexCurrencyQuotes(
            @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {

        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);

        return quoteService.getForexCurrencyQuotes(pageable);
    }
}
