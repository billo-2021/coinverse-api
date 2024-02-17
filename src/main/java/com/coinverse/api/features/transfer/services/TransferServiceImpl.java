package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.features.transfer.mappers.TransferMapper;
import com.coinverse.api.features.transfer.models.DepositRequest;
import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.models.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final UserAccountService userAccountService;
    private final PaymentService paymentService;

    private final TransferMapper transferMapper;

    @Override
    public PaymentResponse deposit(DepositRequest depositRequest) {
        final PaymentRequest paymentRequest = transferMapper.depositRequestToPaymentRequest(depositRequest);
        return paymentService.makePayment(paymentRequest);
    }

    @Override
    public PaymentResponse withdraw(WithdrawRequest withdrawRequest) {
        final PaymentRequest paymentRequest = transferMapper.withdrawRequestToPaymentRequest(withdrawRequest);
        return paymentService.makePayment(paymentRequest);
    }

    @Override
    public PageResponse<PaymentResponse> getTransactions(Pageable pageable) {
        final UserAccount currentUser = userAccountService.getCurrentUser();
        return paymentService.getPayments(currentUser.getUsername(), pageable);
    }

    @Override
    public PaymentResponse getTransactionById(Long id) {
        final UserAccount currentUser = userAccountService.getCurrentUser();
        return paymentService.getPaymentByUsernameAndPaymentId(currentUser.getUsername(), id);
    }
}
