package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.common.validators.AccountRequestValidator;
import com.coinverse.api.features.transfer.mappers.PaymentMapper;
import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.validators.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProcessor paymentProcessor;

    private final PaymentValidator paymentValidator;
    private final AccountRequestValidator accountRequestValidator;
    private final PaymentMapper paymentMapper;

    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        return paymentProcessor.makePayment(paymentRequest);
    }

    public PageResponse<PaymentResponse> getPayments(String username,
                                                     Pageable pageable) {
        final Account userAccount = accountRequestValidator.validateUsername(username);
        final Page<Payment> paymentPage = paymentRepository.findAllByAccountId(userAccount.getId(), pageable);
        final Page<PaymentResponse> paymentResponsePage = paymentPage.map(paymentMapper::paymentToPaymentResponse);

        return PageResponse.of(paymentResponsePage);
    }

    public PaymentResponse getPaymentByUsernameAndPaymentId(String username,
                                                            Long paymentId) {
        final Account userAccount = accountRequestValidator.validateUsername(username);
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindNotFoundException("payment", "paymentId", String.valueOf(paymentId)));

        if (!payment.getAccount().getId().equals(userAccount.getId())) {
            throw ErrorMessageUtils.getInvalidRequestException();
        }

        return paymentMapper.paymentToPaymentResponse(payment);
    }
}
