package com.coinverse.api.features.balance.controllers;

import com.coinverse.api.common.config.routes.BalanceRoutes;
import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.balance.models.WalletResponse;
import com.coinverse.api.features.balance.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(BalanceRoutes.PATH)
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;

    @GetMapping
    public PageResponse<WalletResponse> getBalances(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                    @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                    @RequestParam(value = "sortBy", defaultValue = "balance", required = false) String sortBy,
                                                    @RequestParam(value = "sortDirection", defaultValue = "desc", required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);

        return balanceService.getBalances(pageable);
    }

    @GetMapping(BalanceRoutes.ALL_BALANCES)
    public ResponseEntity<PageResponse<WalletResponse>> getAllBalances() {
        final PageResponse<WalletResponse> walletPageResponse = balanceService.getAllBalances();
        return ResponseEntity.ok(walletPageResponse);
    }

    @GetMapping(BalanceRoutes.GET_BALANCE_BY_ID)
    public ResponseEntity<WalletResponse> getBalancesByWalletId(@PathVariable Long id) {
        final WalletResponse walletResponse = balanceService.getBalancesByWalletId(id);

        return ResponseEntity.ok(walletResponse);
    }
}
