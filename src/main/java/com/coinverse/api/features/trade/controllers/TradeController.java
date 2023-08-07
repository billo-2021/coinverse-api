package com.coinverse.api.features.trade.controllers;

import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.services.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TradeController.PATH)
@RequiredArgsConstructor
public class TradeController {
    public static final String PATH = "/api/v1/trades";

    private final TradeService tradeService;
    @PostMapping
    public ResponseEntity<CurrencyTransactionResponse> requestTrade(@Valid @RequestBody TradeRequest tradeRequest) {
        final CurrencyTransactionResponse transactionResponse = tradeService.requestTrade(tradeRequest);
        return ResponseEntity.ok(transactionResponse);
    }
    @GetMapping
    public PageResponse<CurrencyTransactionResponse> getTrades(
            @RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {

        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);

        return tradeService.getTrades(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CurrencyTransactionResponse> getTradeById(@PathVariable Long id) {
        final CurrencyTransactionResponse transactionResponse = tradeService.getTradeById(id);
        return ResponseEntity.ok(transactionResponse);
    }
}
