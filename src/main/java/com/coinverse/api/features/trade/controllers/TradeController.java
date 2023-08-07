package com.coinverse.api.features.trade.controllers;

import com.coinverse.api.common.config.routes.TradeRoutes;
import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.services.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TradeRoutes.PATH)
@RequiredArgsConstructor
public class TradeController {
    private final TradeService tradeService;
    @PostMapping
    public CurrencyTransactionResponse requestTrade(@Valid @RequestBody TradeRequest tradeRequest) {
        return tradeService.requestTrade(tradeRequest);
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

    @GetMapping(TradeRoutes.GET_TRADE_BY_ID)
    public CurrencyTransactionResponse getTradeById(@PathVariable Long id) {
        return tradeService.getTradeById(id);
    }
}
