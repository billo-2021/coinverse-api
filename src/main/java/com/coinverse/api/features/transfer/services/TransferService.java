package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.transfer.models.DepositRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.models.WithdrawRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;

public interface TransferService {
    PaymentResponse deposit(@NotNull final DepositRequest depositRequest);
    PaymentResponse withdraw(@NotNull final WithdrawRequest withdrawRequest);

    PageResponse<PaymentResponse> getTransactions(@NotNull final PageRequest pageRequest);
    PaymentResponse getTransactionById(@NotNull final Long id);
}
