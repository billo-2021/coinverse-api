package com.coinverse.api.features.transfer.controllers;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.transfer.models.DepositRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.models.WithdrawRequest;
import com.coinverse.api.features.transfer.services.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TransferController.PATH)
@RequiredArgsConstructor
public class TransferController {
    public static final String PATH = "/api/v1/transfers";

    private final TransferService transferService;
    private final PageRequestValidator pageRequestValidator;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        final PaymentResponse paymentResponse = transferService.deposit(depositRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<PaymentResponse> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        final PaymentResponse paymentResponse = transferService.withdraw(withdrawRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/transactions")
    public ResponseEntity<PageResponse<PaymentResponse>> getTransactions(@RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize) {
        final PageRequest pageRequest = pageRequestValidator.validatePageRequest(pageNumber, pageSize);
        final PageResponse<PaymentResponse> paymentPageResponse = transferService.getTransactions(pageRequest);

        return ResponseEntity.ok(paymentPageResponse);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<PaymentResponse> getTransactionById(@PathVariable Long id) {
        final PaymentResponse paymentResponse = transferService.getTransactionById(id);
        return ResponseEntity.ok(paymentResponse);
    }
}
