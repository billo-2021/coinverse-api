package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.constants.ErrorMessage;
import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.CurrencyTransactionActionEnum;
import com.coinverse.api.common.models.CurrencyTransactionStatusEnum;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.security.services.UserAccountService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.core.utils.BigDecimalUtil;
import com.coinverse.api.core.utils.DateTimeUtil;
import com.coinverse.api.features.trade.mappers.TradeMapper;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import static com.coinverse.api.common.utils.MathUtil.ROUNDING_CONTEXT;

@Service
@RequiredArgsConstructor
public class TradeProcessorImpl implements TradeProcessor {
    public final static ChronoUnit EXPIRATION_OFFSET_UNIT = ChronoUnit.SECONDS;

    private final UserAccountService userAccountService;
    private final WalletRepository walletRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private final CurrencyTransactionActionRepository currencyTransactionActionRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyTransactionRepository currencyTransactionRepository;
    private final CurrencyTransactionStatusRepository currencyTransactionStatusRepository;

    private final TradeMapper tradeMapper;

    @Override
    public CurrencyTransactionResponse requestTrade(TradeRequest tradeRequest) {
        final Account account = userAccountService.getCurrentUserAccount();
        final BigDecimal tradeAmount = BigDecimal.valueOf(tradeRequest.getAmount()).round(ROUNDING_CONTEXT);
        final String amountCurrencyCode = tradeRequest.getAmountCurrencyCode();
        final Long quoteId = tradeRequest.getQuoteId();
        final String tradeActionCode = tradeRequest.getAction();

        final Currency amountCurrency = currencyRepository.findByCodeIgnoreCase(amountCurrencyCode)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("amountCurrencyCode", amountCurrencyCode));

        final CurrencyExchangeRate tradeExchangeRate = currencyExchangeRateRepository
                .findById(quoteId)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("quoteId", String.valueOf(quoteId)));

        final Integer quoteTimeToLive = tradeExchangeRate.getTimeToLive();

        if (!DateTimeUtil.isBeforeNow(tradeExchangeRate.getCreatedAt(), quoteTimeToLive, EXPIRATION_OFFSET_UNIT)) {
            throw ErrorMessageUtils.getInvalidRequestException(ErrorMessage.QUOTE_EXPIRED.getMessage());
        }

        final CurrencyPair tradeCurrencyPair = tradeExchangeRate.getCurrencyPair();
        final Currency tradeBaseCurrency = tradeCurrencyPair.getBaseCurrency();
        final Currency tradeQuoteCurrency = tradeCurrencyPair.getQuoteCurrency();

        if (!amountCurrencyCode.equalsIgnoreCase(tradeBaseCurrency.getCode()) &&
                !amountCurrencyCode.equalsIgnoreCase(tradeQuoteCurrency.getCode())) {
            throw ErrorMessageUtils.getValidationException("amountCurrencyCode", amountCurrencyCode);
        }

        final CurrencyTransactionActionEnum tradeActionEnum = CurrencyTransactionActionEnum.of(tradeActionCode)
                .orElseThrow(() -> ErrorMessageUtils.getValidationException("action", tradeActionCode));
        final CurrencyTransactionAction tradeAction = currencyTransactionActionRepository
                .findByCodeIgnoreCase(tradeActionCode)
                .orElseThrow();

        final boolean isBuying = tradeActionEnum == CurrencyTransactionActionEnum.BUY;

        final Wallet sourceWallet = walletRepository.findByAccountIdAndCurrencyId(account.getId(),
                        isBuying ? tradeQuoteCurrency.getId() : tradeBaseCurrency.getId())
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("sourceWallet", "currencyCode", tradeQuoteCurrency.getCode()));

        final Wallet targetWallet = walletRepository.findByAccountIdAndCurrencyId(account.getId(),
                        isBuying ? tradeBaseCurrency.getId() : tradeQuoteCurrency.getId())
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("targetWallet", "currencyCode", tradeBaseCurrency.getCode()));

        final boolean isAmountSameAsBaseCurrency = amountCurrency.getId().equals(tradeBaseCurrency.getId());
        final BigDecimal buyAmount;
        final BigDecimal sellAmount;

        if (isBuying && isAmountSameAsBaseCurrency) {
            buyAmount = tradeAmount;
            sellAmount = BigDecimalUtil.multiply(tradeAmount, tradeExchangeRate.getAskRate());
        } else if (isBuying) {
            buyAmount = BigDecimalUtil.divide(tradeAmount, tradeExchangeRate.getAskRate());
            sellAmount = tradeAmount;
        } else if (isAmountSameAsBaseCurrency) {
            buyAmount = BigDecimalUtil.multiply(tradeAmount, tradeExchangeRate.getBidRate());
            sellAmount = tradeAmount;
        } else {
            buyAmount = tradeAmount;
            sellAmount = BigDecimalUtil.divide(tradeAmount, tradeExchangeRate.getBidRate());
        }

        final BigDecimal newTargetBalance =  BigDecimalUtil.add(targetWallet.getBalance(), buyAmount).round(ROUNDING_CONTEXT);
        final BigDecimal newSourceBalance = BigDecimalUtil.subtract(sourceWallet.getBalance(), sellAmount).round(ROUNDING_CONTEXT);

        if (BigDecimalUtil.isNegative(newSourceBalance) || BigDecimalUtil.isNegative(newTargetBalance)) {
            throw ErrorMessageUtils.getInvalidRequestException(ErrorMessage.INSUFFICIENT_FUNDS.getMessage());
        }

        sourceWallet.setBalance(newSourceBalance);
        targetWallet.setBalance(newTargetBalance);

        walletRepository.saveAndFlush(sourceWallet);
        walletRepository.saveAndFlush(targetWallet);

        final String createdTransactionStatusCode = CurrencyTransactionStatusEnum.CREATED.getCode();
        final CurrencyTransactionStatus transactionStatus = currencyTransactionStatusRepository.findByCodeIgnoreCase(createdTransactionStatusCode)
                .orElseThrow(() -> ErrorMessageUtils.getUnableToFindMappingException("transactionStatus", "transactionStatusCode", createdTransactionStatusCode));

        final CurrencyTransaction transaction = CurrencyTransaction
                .builder()
                .amount(buyAmount.round(ROUNDING_CONTEXT))
                .currency(targetWallet.getCurrency())
                .action(tradeAction)
                .account(account)
                .sourceWallet(sourceWallet)
                .destinationWallet(targetWallet)
                .status(transactionStatus)
                .build();

        final CurrencyTransaction createdTransaction = currencyTransactionRepository.saveAndFlush(transaction);
        return tradeMapper.currencyTransactionToCurrencyTransactionResponse(createdTransaction);
    }
}
