package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.transfer.models.DepositRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.models.WithdrawRequest;
import org.springframework.data.domain.Pageable;

public interface TransferService {
    PaymentResponse deposit(DepositRequest depositRequest);
    PaymentResponse withdraw(WithdrawRequest withdrawRequest);

    PageResponse<PaymentResponse> getTransactions(Pageable pageable);
    PaymentResponse getTransactionById(Long id);
}
