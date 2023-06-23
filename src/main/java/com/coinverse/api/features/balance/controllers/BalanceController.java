package com.coinverse.api.features.balance.controllers;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.balance.models.WalletResponse;
import com.coinverse.api.features.balance.services.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BalanceController.PATH)
@RequiredArgsConstructor
public class BalanceController {
    public static final String PATH = "/api/v1/balances";

    private final BalanceService balanceService;

    @GetMapping
    public ResponseEntity<PageResponse<WalletResponse>> getBalances() {
        final PageResponse<WalletResponse> balancePage = balanceService.getBalances();
        return ResponseEntity.ok(balancePage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WalletResponse> getBalanceByWalletId(@PathVariable("id") Long id) {
        final WalletResponse walletResponse = balanceService.getBalancesByWalletId(id);

        return ResponseEntity.ok(walletResponse);
    }
}
