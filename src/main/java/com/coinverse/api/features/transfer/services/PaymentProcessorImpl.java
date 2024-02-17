package com.coinverse.api.features.transfer.services;

import com.coinverse.api.common.constants.ErrorMessage;
import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.PaymentActionEnum;
import com.coinverse.api.common.models.PaymentStatusEnum;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.core.utils.BigDecimalUtil;
import com.coinverse.api.features.transfer.mappers.PaymentMapper;
import com.coinverse.api.features.transfer.models.PaymentRequest;
import com.coinverse.api.features.transfer.models.PaymentResponse;
import com.coinverse.api.features.transfer.validators.PaymentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.coinverse.api.common.utils.MathUtil.ROUNDING_CONTEXT;

@Service
@RequiredArgsConstructor
public class PaymentProcessorImpl implements PaymentProcessor {
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private final WalletRepository walletRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusRepository paymentStatusRepository;
    private final UserAccountService userAccountService;

    private final PaymentValidator paymentValidator;

    private final PaymentMapper paymentMapper;

    @Override
    @Transactional
    public PaymentResponse makePayment(PaymentRequest paymentRequest) {
        final Account account = userAccountService.getCurrentUserAccount();
        final Account systemAccount = getSystemAccount();
        final PaymentMethod paymentMethod = paymentValidator.validatePaymentMethod(paymentRequest.getMethod());
        final PaymentActionEnum paymentActionEnum = paymentValidator.validatePaymentActionEnum(paymentRequest.getAction());
        final PaymentAction paymentAction = paymentValidator.validatePaymentAction(paymentActionEnum.getCode());
        final boolean isDepositing = paymentActionEnum == PaymentActionEnum.DEPOSIT;

        final BigDecimal paymentAmount = BigDecimal.valueOf(paymentRequest.getAmount()).round(ROUNDING_CONTEXT);
        final String amountCurrencyCode = paymentRequest.getAmountCurrencyCode();
        final Currency amountCurrency = paymentValidator.validateAmountCurrencyCode(paymentRequest.getAmountCurrencyCode());
        String paymentFromCurrencyCode = paymentRequest.getFromCurrencyCode();
        String paymentToCurrencyCode = paymentRequest.getToCurrencyCode();
        final String paymentCurrencyPairName = paymentRequest.getCurrencyPairName();

        final boolean isCurrencyPairNameNotProvided = paymentCurrencyPairName == null;
        paymentValidator.validatePaymentCurrency(paymentCurrencyPairName, paymentFromCurrencyCode, paymentToCurrencyCode);

        final boolean isFromCurrencyCodeSameIsToCurrencyCode = isCurrencyPairNameNotProvided && paymentFromCurrencyCode.equalsIgnoreCase(paymentToCurrencyCode);
        Currency baseCurrency = null;
        Currency quoteCurrency = null;
        CurrencyExchangeRate currencyExchangeRate = null;
        final Currency paymentFromCurrency;
        final Currency paymentToCurrency;

        if (!isFromCurrencyCodeSameIsToCurrencyCode) {
            final CurrencyPair paymentCurrencyPair = paymentValidator.validateCurrencyPair(paymentFromCurrencyCode, paymentToCurrencyCode, paymentCurrencyPairName);
            baseCurrency = paymentCurrencyPair.getBaseCurrency();
            quoteCurrency = paymentCurrencyPair.getQuoteCurrency();
            currencyExchangeRate = currencyExchangeRateRepository.findFirstByCurrencyPairId(paymentCurrencyPair.getId())
                    .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("exchangeRate", "currencyPairId", String.valueOf(paymentCurrencyPair.getId())));
        }

        if (isCurrencyPairNameNotProvided) {
            paymentFromCurrency = paymentValidator.validateFromCurrencyCode(paymentFromCurrencyCode);
            paymentToCurrency = paymentValidator.validateToCurrencyCode(paymentToCurrencyCode);
        } else {
            paymentFromCurrency = quoteCurrency;
            paymentFromCurrencyCode = quoteCurrency.getCode();
            paymentToCurrency = baseCurrency;
        }

        if (isFromCurrencyCodeSameIsToCurrencyCode && !amountCurrencyCode.equalsIgnoreCase(paymentFromCurrencyCode)) {
            throw ErrorMessageUtils.getValidationException("amountCurrencyCode", amountCurrencyCode);
        }

        final boolean isBuying = !isFromCurrencyCodeSameIsToCurrencyCode && paymentFromCurrency.getId().equals(quoteCurrency.getId());
        final boolean isAmountSameAsBaseCurrency = !isFromCurrencyCodeSameIsToCurrencyCode && amountCurrency.getId().equals(baseCurrency.getId());
        final Currency creditCurrency = isDepositing ? paymentToCurrency : paymentFromCurrency;
        final Currency debitCurrency = isDepositing ? paymentToCurrency : paymentFromCurrency;
        final BigDecimal buyAmount;
        final BigDecimal sellAmount;

        if (isFromCurrencyCodeSameIsToCurrencyCode) {
            buyAmount = paymentAmount;
            sellAmount = paymentAmount;
        } else if (isBuying && isAmountSameAsBaseCurrency) {
            buyAmount = paymentAmount;
            sellAmount = BigDecimalUtil.multiply(paymentAmount, currencyExchangeRate.getAskRate());
        } else if (isBuying) {
            buyAmount = BigDecimalUtil.divide(paymentAmount, currencyExchangeRate.getAskRate());
            sellAmount = paymentAmount;
        } else if (isAmountSameAsBaseCurrency) {
            buyAmount = BigDecimalUtil.multiply(paymentAmount, currencyExchangeRate.getBidRate());
            sellAmount = paymentAmount;
        } else {
            buyAmount = paymentAmount;
            sellAmount = BigDecimalUtil.divide(paymentAmount, currencyExchangeRate.getBidRate());
        }

        final long creditAccountId = isDepositing ? account.getId() : systemAccount.getId();
        final long debitAccountId = isDepositing ? systemAccount.getId() : account.getId();
        final long creditCurrencyId = creditCurrency.getId();
        final long debitCurrencyId = debitCurrency.getId();

        final Wallet walletToCredit = walletRepository.findByAccountIdAndCurrencyId(creditAccountId, creditCurrencyId)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("walletToCredit", "currencyId", String.valueOf(creditCurrencyId)));

        final Wallet walletToDebit = walletRepository.findByAccountIdAndCurrencyId(debitAccountId, debitCurrencyId)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("walletToDebit", "currencyId", String.valueOf(debitCurrencyId)));

        walletToCredit.setBalance(BigDecimalUtil.add(walletToCredit.getBalance(), buyAmount).round(ROUNDING_CONTEXT));
        walletToDebit.setBalance(BigDecimalUtil.subtract(walletToDebit.getBalance(), sellAmount).round(ROUNDING_CONTEXT));

        if (BigDecimalUtil.isNegative(walletToCredit.getBalance()) || BigDecimalUtil.isNegative(walletToDebit.getBalance())) {
            throw ErrorMessageUtils.getInvalidRequestException(ErrorMessage.INSUFFICIENT_FUNDS);
        }

        final String createdPaymentStatusCode = PaymentStatusEnum.CREATED.getCode();
        final PaymentStatus paymentStatus = paymentStatusRepository.findByCodeIgnoreCase(createdPaymentStatusCode)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("paymentStatus", "createdPaymentStatusCode", createdPaymentStatusCode));

        final Payment creditTransaction = getPayment(buyAmount, creditCurrency, walletToCredit.getAccount(), paymentMethod, paymentAction, paymentStatus);
        final Payment debitTransaction =  getPayment(sellAmount, debitCurrency, walletToDebit.getAccount(), paymentMethod, paymentAction, paymentStatus);

        walletRepository.saveAndFlush(walletToCredit);
        walletRepository.saveAndFlush(walletToDebit);

        final Payment createdCreditTransaction = paymentRepository.saveAndFlush(creditTransaction);
        final Payment createdDebitTransaction = paymentRepository.saveAndFlush(debitTransaction);
        return paymentMapper.paymentToPaymentResponse(isDepositing ? createdCreditTransaction : createdDebitTransaction);
    }

    private Account getSystemAccount() {
        return paymentValidator.validateSystemAccount();
    }

    private Payment getPayment(BigDecimal amount, Currency currency, Account account, PaymentMethod paymentMethod, PaymentAction paymentAction, PaymentStatus paymentStatus) {
        return Payment
                .builder()
                .amount(amount.round(ROUNDING_CONTEXT))
                .currency(currency)
                .account(account)
                .method(paymentMethod)
                .action(paymentAction)
                .status(paymentStatus)
                .build();
    }
}
