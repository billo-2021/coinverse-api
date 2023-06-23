package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.PaymentActionEnum;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.features.transfer.mappers.PaymentMapper;
import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.validators.PaymentValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final AccountRepository accountRepository;
    private final PaymentActionRepository paymentActionRepository;
    private final StableCoinRepository stableCoinRepository;
    private final WalletRepository walletRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentRepository paymentRepository;

    private final PaymentValidator paymentValidator;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponse makePayment(@NotNull final String username, PaymentRequest paymentRequest) {
        Account userAccount = accountRepository.findByUsername(username).orElseThrow();

        Account systemAccount = accountRepository.findByUsername("system@coinverse.com").orElseThrow();
        PaymentMethod paymentMethod = paymentMethodRepository.findByName(paymentRequest.getMethod()).orElseThrow();
        PaymentActionEnum paymentActionEnum = PaymentActionEnum.of(paymentRequest.getAction()).orElseThrow();

        PaymentAction paymentAction = paymentActionRepository.findByName(paymentActionEnum.getName()).orElseThrow();

        final String paymentCurrencyCode = paymentRequest.getCurrencyCode();

        StableCoin paymentCurrencyStableCoin = stableCoinRepository.findByCurrencyCode(paymentCurrencyCode).orElseThrow();
        CryptoCurrency paymentCurrencyCryptoCurrency = paymentCurrencyStableCoin.getCryptoCurrency();

        List<Wallet> userWallets = walletRepository.findByAccountId(userAccount.getId());

        Wallet targetWallet = userWallets.stream()
                .filter((userWallet) -> {
                    CryptoCurrency userWalletCryptoCurrency = userWallet.getCurrency();
                    return userWalletCryptoCurrency.getId().equals(paymentCurrencyCryptoCurrency.getId());
                })
                .findFirst()
                .orElseThrow();

        List<Wallet> systemWallets = walletRepository.findByAccountId(systemAccount.getId());

        Wallet paymentMatchingSystemWallet = systemWallets.stream()
                .filter((systemWallet) -> {
                    CryptoCurrency systemWalletCryptoCurrency = systemWallet.getCurrency();
                    return systemWalletCryptoCurrency.getId().equals(paymentCurrencyCryptoCurrency.getId());
                })
                .findFirst()
                .orElseThrow();

        BigDecimal paymentAmount = BigDecimal.valueOf(paymentRequest.getAmount());

        if (paymentActionEnum == PaymentActionEnum.DEPOSIT) {
            BigDecimal newTargetWalletBalance = targetWallet.getBalance().add(paymentAmount);
            targetWallet.setBalance(newTargetWalletBalance);

            BigDecimal newSystemWalletBalance = paymentMatchingSystemWallet.getBalance().subtract(paymentAmount);
            paymentMatchingSystemWallet.setBalance(newSystemWalletBalance);

        } else if (paymentActionEnum == PaymentActionEnum.WITHDRAW) {
            BigDecimal newTargetWalletBalance = targetWallet.getBalance().subtract(paymentAmount);

            if (newTargetWalletBalance.compareTo(BigDecimal.valueOf(0.00)) < 0) {
                throw new InvalidRequestException("Not enough money in the balance");
            }

            targetWallet.setBalance(newTargetWalletBalance);

            BigDecimal newSystemWalletBalance = paymentMatchingSystemWallet.getBalance().add(paymentAmount);
            paymentMatchingSystemWallet.setBalance(newSystemWalletBalance);
        }

        Payment payment = Payment
                .builder()
                .amount(paymentAmount)
                .currency(paymentCurrencyStableCoin.getCurrency())
                .method(paymentMethod)
                .action(paymentAction)
                .build();

        walletRepository.save(targetWallet);
        walletRepository.save(paymentMatchingSystemWallet);

        final Payment createdPayment = paymentRepository.save(payment);
        return paymentMapper.paymentToPaymentResponse(createdPayment);
    }

    public PageResponse<PaymentResponse> getPayments(@NotNull final String username,
                                                     @NotNull final PageRequest pageRequest) {
        final Account userAccount = paymentValidator.validatePaymentUsername(username);
        final Page<Payment> paymentPage = paymentRepository.findAllByAccountId(userAccount.getId(), pageRequest);
        final Page<PaymentResponse> paymentResponsePage = paymentPage.map(paymentMapper::paymentToPaymentResponse);

        return PageResponse.of(paymentResponsePage);
    }

    public PaymentResponse getPaymentByUsernameAndPaymentId(@NotNull final String username,
                                                            @NotNull final Long paymentId) {
        final Account userAccount = paymentValidator.validatePaymentUsername(username);
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(InvalidRequestException::new);

        if (!payment.getAccount().getId().equals(userAccount.getId())) {
            throw new InvalidRequestException();
        }

        return paymentMapper.paymentToPaymentResponse(payment);
    }
}
