package com.coinverse.api.features.transfer.controllers;

import com.coinverse.api.common.config.routes.TransferRoutes;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(TransferRoutes.PATH)
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;

    @PostMapping(TransferRoutes.DEPOSIT)
    public PaymentResponse deposit(@Valid @RequestBody DepositRequest depositRequest) {
        return transferService.deposit(depositRequest);
    }

    @PostMapping(TransferRoutes.WITHDRAW)
    public PaymentResponse withdraw(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        return transferService.withdraw(withdrawRequest);
    }

    @GetMapping(TransferRoutes.TRANSACTIONS)
    public PageResponse<PaymentResponse> getTransactions(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                                         @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                                         @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                         @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);
        return transferService.getTransactions(pageable);
    }

    @GetMapping(TransferRoutes.GET_TRANSACTION_BY_ID)
    public PaymentResponse getTransactionById(@PathVariable Long id) {
        return transferService.getTransactionById(id);
    }
}
