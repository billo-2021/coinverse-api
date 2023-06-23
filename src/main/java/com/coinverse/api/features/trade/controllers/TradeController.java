package com.coinverse.api.features.trade.controllers;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.trade.models.CryptoTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.models.WalletResponse;
import com.coinverse.api.features.trade.services.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TradeController.PATH)
@RequiredArgsConstructor
public class TradeController {
    public static final String PATH = "/api/v1/trades";

    private final TradeService tradeService;

    private final PageRequestValidator pageRequestValidator;
    @PostMapping
    public ResponseEntity<CryptoTransactionResponse> requestTrade(@Valid @RequestBody TradeRequest tradeRequest) {
        final CryptoTransactionResponse transactionResponse = tradeService.requestTrade(tradeRequest);
        return ResponseEntity.ok(transactionResponse);
    }
    @GetMapping
    public ResponseEntity<PageResponse<CryptoTransactionResponse>> getTrades(
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {

        final PageResponse<CryptoTransactionResponse> transactionPageResponse = tradeService.getTrades(
                pageRequestValidator.validatePageRequest(pageNumber, pageSize));

        return ResponseEntity.ok(transactionPageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CryptoTransactionResponse> getTrade(@PathVariable final Long id) {
        final CryptoTransactionResponse transactionResponse = tradeService.getTradeById(id);
        return ResponseEntity.ok(transactionResponse);
    }

    @GetMapping("/wallets")
    public ResponseEntity<PageResponse<WalletResponse>> getWallets(@RequestParam(required = false) Integer pageNumber,
                                                                   @RequestParam(required = false) Integer pageSize) {
        final PageResponse<WalletResponse> walletPageResponse = tradeService.getWallets(pageRequestValidator
                .validatePageRequest(pageNumber, pageSize));

        return ResponseEntity.ok(walletPageResponse);
    }

    @GetMapping("/wallets/all")
    public ResponseEntity<PageResponse<WalletResponse>> getAllWallets() {
        final PageResponse<WalletResponse> walletPageResponse = tradeService.getAllWallets();

        return ResponseEntity.ok(walletPageResponse);
    }

    @GetMapping("/wallets/{id}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable Long id) {
        final WalletResponse walletResponse = tradeService.getWalletById(id);
        return ResponseEntity.ok(walletResponse);
    }
}
