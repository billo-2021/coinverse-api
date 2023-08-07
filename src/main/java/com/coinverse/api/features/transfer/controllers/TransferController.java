package com.coinverse.api.features.transfer.controllers;

import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.transfer.models.DepositRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.models.WithdrawRequest;
import com.coinverse.api.features.transfer.services.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TransferController.PATH)
@RequiredArgsConstructor
public class TransferController {
    public static final String PATH = "/api/v1/transfers";
    private final TransferService transferService;

    @PostMapping("/deposit")
    public ResponseEntity<PaymentResponse> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        final PaymentResponse paymentResponse = transferService.deposit(depositRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<PaymentResponse> withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        final PaymentResponse paymentResponse = transferService.withdraw(withdrawRequest);
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/transactions")
    public PageResponse<PaymentResponse> getTransactions(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                                         @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                                         @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                         @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);
        return transferService.getTransactions(pageable);
    }

    @GetMapping("/transactions/{id}")
    public PaymentResponse getTransactionById(@PathVariable Long id) {
        return transferService.getTransactionById(id);
    }
}
