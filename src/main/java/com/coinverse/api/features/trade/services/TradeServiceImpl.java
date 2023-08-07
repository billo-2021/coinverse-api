package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.exceptions.ValidationException;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.features.trade.mappers.TradeMapper;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.validators.TradeValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.coinverse.api.common.utils.MathUtil.MATH_CONTEXT;
import static com.coinverse.api.common.utils.MathUtil.ROUNDING_CONTEXT;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final WalletRepository walletRepository;
    private final CurrencyExchangeRateRepository currencyExchangeRateRepository;
    private final CurrencyTransactionActionRepository currencyTransactionActionRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyTransactionStatusRepository currencyTransactionStatusRepository;
    private final CurrencyTransactionRepository currencyTransactionRepository;

    private final TradeValidator tradeValidator;
    private final TradeMapper tradeMapper;

    @Transactional
    @Override
    public CurrencyTransactionResponse requestTrade(TradeRequest tradeRequest) {
        final UserAccount userAccount = getCurrentUser();
        final Account account = tradeValidator.validateTradeUsername(userAccount.getUsername());

        final BigDecimal tradeAmount = BigDecimal.valueOf(tradeRequest.getAmount());
        final String amountCurrencyCode = tradeRequest.getAmountCurrencyCode();
        final Long quoteId = tradeRequest.getQuoteId();
        final String tradeActionCode = tradeRequest.getAction();


        final Currency amountCurrency = currencyRepository.findByCodeIgnoreCase(amountCurrencyCode)
                .orElseThrow(() ->
                        new ValidationException("Invalid amountCurrencyCode '" + amountCurrencyCode
                                + "'", "amountCurrencyCode")
                );

        final CurrencyExchangeRate tradeExchangeRate = currencyExchangeRateRepository
                .findById(quoteId)
                .orElseThrow(() ->
                        new ValidationException("Invalid quoteId '" + quoteId + "'", "quoteId")
                );

        final Integer quoteTimeToLive = tradeExchangeRate.getTimeToLive();
        final OffsetDateTime quoteExpirationDateTime = tradeExchangeRate.getCreatedAt().plusSeconds(quoteTimeToLive);
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        if (now.isAfter(quoteExpirationDateTime)) {
            throw new InvalidRequestException("Quote has expired");
        }

        final CurrencyPair tradeCurrencyPair = tradeExchangeRate.getCurrencyPair();
        final Currency tradeBaseCurrency = tradeCurrencyPair.getBaseCurrency();
        final Currency tradeQuoteCurrency = tradeCurrencyPair.getQuoteCurrency();

        if (!amountCurrencyCode.equalsIgnoreCase(tradeBaseCurrency.getCode()) &&
                !amountCurrencyCode.equalsIgnoreCase(tradeQuoteCurrency.getCode())) {
            throw new ValidationException("Invalid amountCurrencyCode '" + amountCurrencyCode + "'", "amountCurrencyCode");
        }

        CurrencyTransactionActionEnum tradeActionEnum = CurrencyTransactionActionEnum.of(tradeActionCode)
                .orElseThrow(() ->
                        new ValidationException("Invalid action '" + tradeActionCode + "'", "action")
                );

        final boolean isBuying = tradeActionEnum == CurrencyTransactionActionEnum.BUY;

        final CurrencyTransactionAction tradeAction = currencyTransactionActionRepository
                .findByCodeIgnoreCase(tradeActionCode)
                .orElseThrow();

        Wallet sourceWallet = null;
        Wallet targetWallet = null;

        if (isBuying) {
            sourceWallet = walletRepository.findByAccountIdAndCurrencyId(userAccount.getId(),
                            tradeQuoteCurrency.getId())
                    .orElseThrow(() -> new MappingException("Unable to find target user wallet for currencyCode '" +
                            tradeQuoteCurrency.getCode() + "'"));

            targetWallet = walletRepository.findByAccountIdAndCurrencyId(userAccount.getId(),
                            tradeBaseCurrency.getId())
                    .orElseThrow(() -> new MappingException("Unable to find target user wallet for currencyCode '" +
                            tradeBaseCurrency.getCode() + "'"));
        } else {
            sourceWallet = walletRepository.findByAccountIdAndCurrencyId(userAccount.getId(),
                            tradeBaseCurrency.getId())
                    .orElseThrow(() -> new MappingException("Unable to find target user wallet for currencyCode '" +
                            tradeBaseCurrency.getCode() + "'"));

            targetWallet = walletRepository.findByAccountIdAndCurrencyId(userAccount.getId(),
                            tradeQuoteCurrency.getId())
                    .orElseThrow(() -> new MappingException("Unable to find target user wallet for currencyCode '" +
                            tradeQuoteCurrency.getCode() + "'"));
        }

        final Currency sourceWalletCurrency = sourceWallet.getCurrency();
        final Currency targetWalletCurrency = targetWallet.getCurrency();

        boolean isAmountSameAsBaseCurrency = amountCurrency.getId().equals(tradeBaseCurrency.getId());

        boolean isAmountCurrencySameAsSourceCurrency = amountCurrency.getId().equals(sourceWalletCurrency.getId());
        boolean isAmountCurrencySameAsTargetCurrency = amountCurrency.getId().equals(targetWalletCurrency.getId());

        final BigDecimal rate = isBuying ? tradeExchangeRate.getBidRate() : tradeExchangeRate.getAskRate();

        BigDecimal sameAsTradeBaseCurrencyAmount = isAmountSameAsBaseCurrency ?
                tradeAmount.multiply(rate, MATH_CONTEXT) :
                tradeAmount.divide(rate, MATH_CONTEXT);

        final BigDecimal sourceTradeAmount = isAmountCurrencySameAsSourceCurrency ?
                tradeAmount :
                sameAsTradeBaseCurrencyAmount;

        final BigDecimal targetTradeAmount = isAmountCurrencySameAsTargetCurrency ?
                tradeAmount :
                sameAsTradeBaseCurrencyAmount;

        final BigDecimal sourceBalance = sourceWallet.getBalance();
        final BigDecimal newSourceBalance = sourceBalance.subtract(sourceTradeAmount, MATH_CONTEXT);

        if (newSourceBalance.compareTo(new BigDecimal("0.00000", MATH_CONTEXT)) < 0) {
            throw new InvalidRequestException("Not enough funds in the balance");
        }

        sourceWallet.setBalance(newSourceBalance);

        final BigDecimal targetBalance = targetWallet.getBalance();
        final BigDecimal newTargetBalance =  targetBalance.add(targetTradeAmount, MATH_CONTEXT);

        targetWallet.setBalance(newTargetBalance);

        walletRepository.saveAndFlush(sourceWallet);
        walletRepository.saveAndFlush(targetWallet);

        final String createdTransactionStatusCode = CurrencyTransactionStatusEnum.CREATED.getCode();
        final CurrencyTransactionStatus transactionStatus = currencyTransactionStatusRepository.findByCodeIgnoreCase(createdTransactionStatusCode)
                .orElseThrow(() -> new MappingException("Unable to find transaction status for transactionStatus '" +
                        createdTransactionStatusCode + "'"));

        final CurrencyTransaction transaction = CurrencyTransaction
                .builder()
                .amount(targetTradeAmount.round(ROUNDING_CONTEXT))
                .currency(targetWalletCurrency)
                .action(tradeAction)
                .account(account)
                .sourceWallet(sourceWallet)
                .destinationWallet(targetWallet)
                .status(transactionStatus)
                .build();

        final CurrencyTransaction createdTransaction = currencyTransactionRepository.saveAndFlush(transaction);

        return tradeMapper.currencyTransactionToCurrencyTransactionResponse(createdTransaction);
    }

    @Override
    public PageResponse<CurrencyTransactionResponse> getTrades(Pageable pageable) {
        final UserAccount userAccount = getCurrentUser();
        Account account = tradeValidator.validateTradeUsername(userAccount.getUsername());

        final Page<CurrencyTransaction> currencyTransactionPage = currencyTransactionRepository
                .findAllByAccountId(account.getId(), pageable);

        final Page<CurrencyTransactionResponse> currencyTransactionResponsePage = currencyTransactionPage
                .map(tradeMapper::currencyTransactionToCurrencyTransactionResponse);

        return PageResponse.of(currencyTransactionResponsePage);
    }

    @Override
    public CurrencyTransactionResponse getTradeById(Long id) {
        final UserAccount userAccount = getCurrentUser();
        final Account account = tradeValidator.validateTradeUsername(userAccount.getUsername());
        final Long accountId = account.getId();

        final CurrencyTransaction currencyTransaction = currencyTransactionRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestException("Invalid tradeId"));

        final Account transactionSourceAccount = currencyTransaction.getSourceWallet().getAccount();
        final Long sourceAccountId = transactionSourceAccount.getId();

        Account transactionDestinationAccount = currencyTransaction.getDestinationWallet().getAccount();
        final Long destinationAccountId = transactionDestinationAccount.getId();

        if (!accountId.equals(sourceAccountId) || !accountId.equals(destinationAccountId)) {
            throw new InvalidRequestException();
        }

        return tradeMapper.currencyTransactionToCurrencyTransactionResponse(currencyTransaction);
    }

    private UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
