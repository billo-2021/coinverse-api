package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.NotFoundException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.PaymentActionEnum;
import com.coinverse.api.common.models.PaymentStatusEnum;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.features.transfer.config.TransferProperties;
import com.coinverse.api.features.transfer.mappers.PaymentMapper;
import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.validators.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static com.coinverse.api.common.utils.MathUtil.MATH_CONTEXT;
import static com.coinverse.api.common.utils.MathUtil.ROUNDING_CONTEXT;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final TransferProperties transferProperties;

    private final AccountRepository accountRepository;
    private final CurrencyPairRepository currencyPairRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private final PaymentActionRepository paymentActionRepository;
    private final WalletRepository walletRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;

    private final PaymentValidator paymentValidator;
    private final PaymentMapper paymentMapper;

    @Transactional
    public PaymentResponse makePayment(final String username, PaymentRequest paymentRequest) {
        Account userAccount = accountRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new MappingException("Unable to find user account for username '"
                        + username + "'"));

        Account systemAccount = accountRepository.findByUsernameIgnoreCase(transferProperties.systemUsername())
                .orElseThrow(() -> new MappingException("Unable to find system account for username '"
                        + transferProperties.systemUsername() + "'"));

        PaymentMethod paymentMethod = paymentMethodRepository.findByCodeIgnoreCase(paymentRequest.getMethod())
                .orElseThrow(() ->
                        new ValidationException("Invalid payment method '" + paymentRequest.getMethod() + "'", "method"));

        PaymentActionEnum paymentActionEnum = PaymentActionEnum.of(paymentRequest.getAction())
                .orElseThrow(() ->
                                new ValidationException("Invalid payment action '" + paymentRequest.getAction() + "'", "action")
                        );

        PaymentAction paymentAction = paymentActionRepository.findByCodeIgnoreCase(paymentActionEnum.getCode())
                .orElseThrow(() -> new MappingException("Unable to find payment action for paymentActionName '"
                        + paymentActionEnum.getCode() + "'"));

        final BigDecimal paymentAmount = BigDecimal.valueOf(paymentRequest.getAmount());
        final String amountCurrencyCode = paymentRequest.getAmountCurrencyCode();
        final Currency amountCurrency = currencyRepository.findByCodeIgnoreCase(amountCurrencyCode)
                .orElseThrow(() -> new ValidationException("Invalid amount currency code '"
                        + amountCurrencyCode + "'", "currencyCode"));

        final String paymentCurrencyPairName = paymentRequest.getCurrencyPairName();

        final boolean isCurrencyPairNameNotProvided = paymentCurrencyPairName == null;
        final boolean isFromCurrencyCodeNotProvided = paymentRequest.getFromCurrencyCode() == null;
        final boolean isToCurrencyCodeNotProvided = paymentRequest.getToCurrencyCode() == null;

        if (isCurrencyPairNameNotProvided && isToCurrencyCodeNotProvided && isFromCurrencyCodeNotProvided) {
            throw new InvalidRequestException("Either provide fromCurrencyCode and toCurrencyCode or currencyPairName");
        } else if (isCurrencyPairNameNotProvided && isFromCurrencyCodeNotProvided) {
            throw new InvalidRequestException("fromCurrencyCode is required");
        } else if (isCurrencyPairNameNotProvided && isToCurrencyCodeNotProvided) {
            throw new InvalidRequestException("toCurrencyCode is required");
        }

        Currency paymentFromCurrency = null;
        Currency paymentToCurrency = null;
        Optional<CurrencyPair> paymentCurrencyPair;

        if (!isFromCurrencyCodeNotProvided && !isToCurrencyCodeNotProvided) {
            paymentFromCurrency = currencyRepository.findByCodeIgnoreCase(paymentRequest.getFromCurrencyCode())
                    .orElseThrow(() -> new ValidationException("Invalid fromCurrencyCode '"
                            + paymentRequest.getFromCurrencyCode() + "'", "fromCurrencyCode"));

            paymentToCurrency = currencyRepository.findByCodeIgnoreCase(paymentRequest.getToCurrencyCode())
                    .orElseThrow(() -> new ValidationException("Invalid toCurrencyCode '"
                            + paymentRequest.getToCurrencyCode() + "'", "toCurrencyCode"));

            paymentCurrencyPair = currencyPairRepository.findByCurrencyCodeCombination(paymentFromCurrency.getCode(),
                    paymentToCurrency.getCode());
        } else {
            final CurrencyPair currencyPair = currencyPairRepository.findByNameIgnoreCase(paymentCurrencyPairName)
                    .orElseThrow(() -> new ValidationException("Invalid paymentCurrencyPairName '"
                            + paymentCurrencyPairName + "'", "paymentCurrencyPairName"));

            paymentCurrencyPair = Optional.of(currencyPair);

            paymentFromCurrency = paymentActionEnum == PaymentActionEnum.DEPOSIT ? currencyPair.getQuoteCurrency() : currencyPair.getBaseCurrency();
            paymentToCurrency = paymentActionEnum == PaymentActionEnum.DEPOSIT ? currencyPair.getBaseCurrency() : currencyPair.getQuoteCurrency();
        }

        final String paymentFromCurrencyCode = paymentFromCurrency.getCode();
        final String paymentToCurrencyCode = paymentToCurrency.getCode();

        if (paymentFromCurrencyCode.equalsIgnoreCase(paymentToCurrencyCode) && !amountCurrencyCode.equalsIgnoreCase(paymentFromCurrencyCode)) {
            throw new ValidationException("Invalid amountCurrencyCode '" + amountCurrencyCode + "'", "amountCurrencyCode");
        }

        Optional<CurrencyExchangeRate> paymentExchangeRate = paymentCurrencyPair.map((currencyPair) ->
                currencyExchangeRateRepository.findFirstByCurrencyPairId(currencyPair.getId())
                        .orElseThrow(() -> new MappingException("Unable to find exchange rate for currency pair")));

        Wallet targetWallet = walletRepository.findByAccountIdAndCurrencyId(userAccount.getId(),
                        paymentActionEnum == PaymentActionEnum.DEPOSIT ? paymentToCurrency.getId() : paymentFromCurrency.getId())
                .orElseThrow(() -> new MappingException("Unable to find target user wallet for currencyCode '" +
                        paymentToCurrencyCode + "'"));

        Wallet systemWallet = null;

        if (paymentActionEnum == PaymentActionEnum.DEPOSIT) {
            systemWallet = walletRepository.findByAccountIdAndCurrencyId(systemAccount.getId(),
                            paymentToCurrency.getId())
                    .orElseThrow(() -> new MappingException("Unable to find system wallet for currencyCode '" +
                            paymentToCurrencyCode + "'"));
        } else {
            systemWallet = walletRepository.findByAccountIdAndCurrencyId(systemAccount.getId(),
                            paymentFromCurrency.getId())
                    .orElseThrow(() -> new MappingException("Unable to find system wallet for currencyCode '" +
                            paymentFromCurrencyCode + "'"));
        }

        CurrencyPair currencyPair = paymentCurrencyPair.orElse(null);

        BigDecimal targetPaymentAmount = paymentExchangeRate.map((exchangeRate) -> {
            final Currency targetCurrency = targetWallet.getCurrency();
            final BigDecimal bidRate = exchangeRate.getBidRate();

            if (amountCurrency.getId().equals(targetCurrency.getId())) {
                return paymentAmount;
            }

            final Currency baseCurrency = currencyPair.getBaseCurrency();

            if (amountCurrency.getId().equals(baseCurrency.getId())) {
                return paymentAmount.multiply(bidRate, MATH_CONTEXT);
            }

            return paymentAmount.divide(bidRate, MATH_CONTEXT);
            }).orElseGet(() -> paymentAmount);

        BigDecimal systemPaymentAmount = paymentExchangeRate.map((exchangeRate) -> {
            final Currency systemWalletCurrency = targetWallet.getCurrency();
            final BigDecimal bidRate = exchangeRate.getBidRate();

            if (amountCurrency.getId().equals(systemWalletCurrency.getId())) {
                return paymentAmount;
            }

            final Currency baseCurrency = currencyPair.getBaseCurrency();

            if (amountCurrency.getId().equals(baseCurrency.getId())) {
                return paymentAmount.multiply(bidRate);
            }

            return paymentAmount.divide(bidRate, MATH_CONTEXT);

        }).orElseGet(() -> paymentAmount);

        final BigDecimal targetBalance = targetWallet.getBalance();
        final BigDecimal newTargetBalance =  paymentActionEnum == PaymentActionEnum.DEPOSIT ?
                targetBalance.add(targetPaymentAmount, MATH_CONTEXT) :
                targetBalance.subtract(targetPaymentAmount, MATH_CONTEXT);

        if (newTargetBalance.compareTo(new BigDecimal("0.00000", MATH_CONTEXT)) < 0) {
                throw new InvalidRequestException("Not enough funds in the balance");
        }

        targetWallet.setBalance(newTargetBalance);

        final BigDecimal systemBalance = systemWallet.getBalance();
        final BigDecimal newSystemBalance = paymentActionEnum == PaymentActionEnum.DEPOSIT ?
                systemBalance.subtract(systemPaymentAmount, MATH_CONTEXT) :
                systemBalance.add(systemPaymentAmount, MATH_CONTEXT);

        systemWallet.setBalance(newSystemBalance);

        final String createdPaymentStatusCode = PaymentStatusEnum.CREATED.getCode();
        final PaymentStatus paymentStatus = paymentStatusRepository.findByCodeIgnoreCase(createdPaymentStatusCode)
                .orElseThrow(() -> new MappingException("Unable to find payment status '"
                        + createdPaymentStatusCode + "'"));

        Payment targetPayment = Payment
                .builder()
                .amount(targetPaymentAmount.round(ROUNDING_CONTEXT))
                .currency(targetWallet.getCurrency())
                .account(userAccount)
                .method(paymentMethod)
                .action(paymentAction)
                .status(paymentStatus)
                .build();

        Payment systemPayment = Payment
                .builder()
                .amount(systemPaymentAmount)
                .currency(systemWallet.getCurrency())
                .account(systemAccount)
                .method(paymentMethod)
                .action(paymentAction)
                .status(paymentStatus)
                .build();

        walletRepository.saveAndFlush(targetWallet);
        walletRepository.saveAndFlush(systemWallet);

        final Payment createdPayment = paymentRepository.saveAndFlush(targetPayment);
        paymentRepository.saveAndFlush(systemPayment);
        return paymentMapper.paymentToPaymentResponse(createdPayment);
    }

    public PageResponse<PaymentResponse> getPayments(String username,
                                                     Pageable pageable) {
        final Account userAccount = paymentValidator.validatePaymentUsername(username);
        final Page<Payment> paymentPage = paymentRepository.findAllByAccountId(userAccount.getId(), pageable);
        final Page<PaymentResponse> paymentResponsePage = paymentPage.map(paymentMapper::paymentToPaymentResponse);

        return PageResponse.of(paymentResponsePage);
    }

    public PaymentResponse getPaymentByUsernameAndPaymentId(String username,
                                                            Long paymentId) {
        final Account userAccount = paymentValidator.validatePaymentUsername(username);
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new NotFoundException("Payment for id '" + paymentId + "' not found"));

        if (!payment.getAccount().getId().equals(userAccount.getId())) {
            throw new InvalidRequestException();
        }

        return paymentMapper.paymentToPaymentResponse(payment);
    }
}
