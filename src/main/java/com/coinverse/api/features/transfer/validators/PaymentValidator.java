package com.coinverse.api.features.transfer.validators;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.PaymentActionEnum;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.transfer.config.TransferProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentValidator {
    private final TransferProperties transferProperties;

    private final AccountRepository accountRepository;
    private final CurrencyPairRepository currencyPairRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentActionRepository paymentActionRepository;
    private final CurrencyRepository currencyRepository;

    public Account validateSystemAccount() {
        return accountRepository.findByUsernameIgnoreCase(transferProperties.systemUsername())
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("systemAccount", "username", transferProperties.systemUsername()));
    }

    public PaymentMethod validatePaymentMethod(String method) {
        return paymentMethodRepository.findByCodeIgnoreCase(method)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("method", method));
    }

    public PaymentActionEnum validatePaymentActionEnum(String action) {
        return PaymentActionEnum.of(action)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("action", action));
    }

    public PaymentAction validatePaymentAction(String action) {
        return paymentActionRepository.findByCodeIgnoreCase(action)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("paymentAction", "paymentActionName", action));
    }

    public Currency validateAmountCurrencyCode(String amountCurrencyCode) {
        return currencyRepository.findByCodeIgnoreCase(amountCurrencyCode)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("amountCurrencyCode", amountCurrencyCode));
    }

    public CurrencyPair validateCurrencyPair(String fromCurrencyCode, String toCurrencyCode, String currencyPairName) {
        final boolean isCurrencyPairNameNotProvided = currencyPairName == null;

        if (isCurrencyPairNameNotProvided) {
            return currencyPairRepository.findByCurrencyCodeCombination(fromCurrencyCode, toCurrencyCode)
                    .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("currencyPair", "currencyPairName", fromCurrencyCode + ", " + toCurrencyCode));
        }

        return currencyPairRepository.findByNameIgnoreCase(currencyPairName)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("currencyPair", "currencyPairName", currencyPairName));
    }

    public Currency validateFromCurrencyCode(String fromCurrencyCode) {
        return currencyRepository.findByCodeIgnoreCase(fromCurrencyCode)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("fromCurrencyCode", fromCurrencyCode));
    }

    public Currency validateToCurrencyCode(String toCurrencyCode) {
        return currencyRepository.findByCodeIgnoreCase(toCurrencyCode)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("toCurrencyCode", toCurrencyCode));
    }

    public void validatePaymentCurrency(String currencyPairName, String fromCurrencyCode, String toCurrencyCode) {
        final boolean isCurrencyPairNameNotProvided = currencyPairName == null;
        final boolean isFromCurrencyCodeNotProvided = fromCurrencyCode == null;
        final boolean isToCurrencyCodeNotProvided = toCurrencyCode == null;

        if (isCurrencyPairNameNotProvided && isToCurrencyCodeNotProvided && isFromCurrencyCodeNotProvided) {
            throw ErrorMessageUtils.getInvalidRequestException("currencyPairName");
        } else if (isCurrencyPairNameNotProvided && isFromCurrencyCodeNotProvided) {
            throw ErrorMessageUtils.getInvalidRequestException("fromCurrencyCode");
        } else if (isCurrencyPairNameNotProvided && isToCurrencyCodeNotProvided) {
            throw ErrorMessageUtils.getInvalidRequestException("toCurrencyCode");
        }
    }
}
