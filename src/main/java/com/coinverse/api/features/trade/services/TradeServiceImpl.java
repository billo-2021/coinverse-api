package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.CryptoTransactionActionEnum;
import com.coinverse.api.common.models.CryptoTransactionStatusEnum;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.features.trade.mappers.TradeMapper;
import com.coinverse.api.features.trade.models.CryptoTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.models.WalletResponse;
import com.coinverse.api.features.trade.validators.TradeValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final WalletService walletService;
    private final WalletRepository walletRepository;
    private final CryptoCurrencyExchangeRateRepository cryptoCurrencyExchangeRateRepository;
    private final CryptoTransactionActionRepository cryptoTransactionActionRepository;
    private final CryptoCurrencyRepository cryptoCurrencyRepository;
    private final CryptoTransactionStatusRepository cryptoTransactionStatusRepository;
    private final CryptoTransactionRepository cryptoTransactionRepository;

    private final TradeValidator tradeValidator;
    private final TradeMapper tradeMapper;

    @Transactional
    @Override
    public CryptoTransactionResponse requestTrade(@NotNull final TradeRequest tradeRequest) {
        final UserAccount userAccount = getCurrentUser();
        final Account account = tradeValidator.validateTradeUsername(userAccount.getUsername());
        final List<Wallet> userWallets = walletRepository.findByAccountId(account.getId());

        final BigDecimal tradeAmount = BigDecimal.valueOf(tradeRequest.getAmount());
        final CryptoCurrencyExchangeRate tradeExchangeRate = cryptoCurrencyExchangeRateRepository
                .findById(tradeRequest.getQuoteId())
                .orElseThrow(InvalidRequestException::new);

        final Integer quoteTimeToLive = tradeExchangeRate.getTimeToLive();
        final OffsetDateTime quoteExpirationDateTime = tradeExchangeRate.getCreatedAt().plusSeconds(quoteTimeToLive);
        final OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        if (now.isAfter(quoteExpirationDateTime)) {
            throw new InvalidRequestException("Quote has expired");
        }

        final CryptoCurrencyPair tradeCurrencyPair = tradeExchangeRate.getCurrencyPair();
        final CryptoCurrency tradeBaseCurrency = tradeCurrencyPair.getBaseCurrency();
        final CryptoCurrency tradeQuoteCurrency = tradeCurrencyPair.getQuoteCurrency();

        final String amountCurrencyCode = tradeRequest.getAmountCurrencyCode();
        final CryptoCurrency amountCurrency = cryptoCurrencyRepository.findByCode(amountCurrencyCode)
                .orElseThrow(InvalidRequestException::new);

        if (!amountCurrency.getId().equals(tradeBaseCurrency.getId()) ||
                !amountCurrency.getId().equals(tradeQuoteCurrency.getId())) {
           throw new InvalidRequestException("Invalid amount currency");
        }

        final CryptoTransactionAction tradeAction = cryptoTransactionActionRepository.findByName(tradeRequest.getAction())
                .orElseThrow();
        final CryptoTransactionActionEnum tradeActionEnum = CryptoTransactionActionEnum.of(tradeAction.getName())
                .orElseThrow();

        final String createdTransactionStatusName = CryptoTransactionStatusEnum.CREATED.getName();
        final CryptoTransactionStatus transactionStatus = cryptoTransactionStatusRepository.findByName(createdTransactionStatusName)
                .orElseThrow(InvalidRequestException::new);

        final CryptoTransaction.CryptoTransactionBuilder transactionBuilder = CryptoTransaction.builder();

        transactionBuilder
                .amount(tradeAmount)
                .amountCurrency(amountCurrency)
                .exchangeRate(tradeExchangeRate)
                .action(tradeAction)
                .status(transactionStatus);

        final boolean isTradeAmountInBaseCurrency = amountCurrency.getId().equals(tradeBaseCurrency.getId());

        if (tradeActionEnum == CryptoTransactionActionEnum.BUY) {
            final BigDecimal askRate = tradeExchangeRate.getAskRate();

            final Wallet sourceWallet = userWallets.stream()
                    .filter(userWallet -> {
                        CryptoCurrency walletCurrency = userWallet.getCurrency();

                        return walletCurrency.getId().equals(tradeQuoteCurrency.getId());
                    }).findFirst().orElseThrow();

            final Wallet destinationWallet = userWallets.stream()
                    .filter(userWallet -> {
                        CryptoCurrency walletCurrency = userWallet.getCurrency();

                        return walletCurrency.getId().equals(tradeBaseCurrency.getId());
                    }).findFirst().orElseThrow();

            final BigDecimal creditAmount = isTradeAmountInBaseCurrency ? tradeAmount : tradeAmount.divide(askRate, RoundingMode.HALF_UP);

            final BigDecimal debitAmount = isTradeAmountInBaseCurrency ? tradeAmount.multiply(askRate) : tradeAmount;

            sourceWallet.setBalance(sourceWallet.getBalance().subtract(debitAmount));
            destinationWallet.setBalance(destinationWallet.getBalance().add(creditAmount));

            transactionBuilder
                    .sourceWallet(sourceWallet)
                    .destinationWallet(destinationWallet);

            walletRepository.save(sourceWallet);
            walletRepository.save(destinationWallet);
        } else {
            final BigDecimal bidRate = tradeExchangeRate.getBidRate();

            final Wallet sourceWallet = userWallets.stream()
                    .filter(userWallet -> {
                        CryptoCurrency walletCurrency = userWallet.getCurrency();
                        return walletCurrency.getId().equals(tradeBaseCurrency.getId());
                    }).findFirst().orElseThrow();

            final Wallet destinationWallet = userWallets.stream()
                    .filter(userWallet -> {
                        CryptoCurrency walletCurrency = userWallet.getCurrency();
                        return walletCurrency.getId().equals(tradeQuoteCurrency.getId());
                    }).findFirst().orElseThrow();

            final BigDecimal creditAmount = isTradeAmountInBaseCurrency ? tradeAmount.divide(bidRate, RoundingMode.HALF_UP) : tradeAmount;

            final BigDecimal debitAmount = isTradeAmountInBaseCurrency ? tradeAmount : tradeAmount.multiply(bidRate);

            sourceWallet.setBalance(sourceWallet.getBalance().subtract(debitAmount));
            destinationWallet.setBalance(destinationWallet.getBalance().add(creditAmount));

            transactionBuilder
                    .sourceWallet(sourceWallet)
                    .destinationWallet(destinationWallet);

            walletRepository.save(sourceWallet);
            walletRepository.save(destinationWallet);
        }

        CryptoTransaction transaction = transactionBuilder.build();
        final CryptoTransaction createdTransaction = cryptoTransactionRepository.save(transaction);

        return tradeMapper.cryptoTransactionToCryptoTransactionResponse(createdTransaction);
    }

    @Override
    public PageResponse<CryptoTransactionResponse> getTrades(@NotNull final PageRequest pageRequest) {
        final UserAccount userAccount = getCurrentUser();
        Account account = tradeValidator.validateTradeUsername(userAccount.getUsername());

        final Page<CryptoTransaction> cryptoTransactionPage = cryptoTransactionRepository
                .findAllByAccountId(account.getId(), pageRequest);

        final Page<CryptoTransactionResponse> cryptoTransactionResponsePage = cryptoTransactionPage
                .map(tradeMapper::cryptoTransactionToCryptoTransactionResponse);

        return PageResponse.of(cryptoTransactionResponsePage);
    }

    @Override
    public CryptoTransactionResponse getTradeById(@NotNull final Long id) {
        final UserAccount userAccount = getCurrentUser();
        final Account account = tradeValidator.validateTradeUsername(userAccount.getUsername());
        final Long accountId = account.getId();

        final CryptoTransaction cryptoTransaction = cryptoTransactionRepository.findById(id)
                .orElseThrow(InvalidRequestException::new);

        final Account transactionSourceAccount = cryptoTransaction.getDestinationWallet().getAccount();
        final Long sourceAccountId = transactionSourceAccount.getId();

        Account transactionDestinationAccount = cryptoTransaction.getDestinationWallet().getAccount();
        final Long destinationAccountId = transactionDestinationAccount.getId();

        if (!accountId.equals(sourceAccountId) || !accountId.equals(destinationAccountId)) {
            throw new InvalidRequestException();
        }

        return tradeMapper.cryptoTransactionToCryptoTransactionResponse(cryptoTransaction);
    }

    @Override
    public PageResponse<WalletResponse> getWallets(@NotNull final PageRequest pageRequest) {
        final UserAccount userAccount = getCurrentUser();
        return walletService.getWallets(userAccount.getUsername(), pageRequest);
    }

    @Override
    public PageResponse<WalletResponse> getAllWallets() {
        final UserAccount userAccount = getCurrentUser();
        return walletService.getAllWallets(userAccount.getUsername());
    }

    @Override
    public WalletResponse getWalletById(@NotNull final Long id) {
        final UserAccount userAccount = getCurrentUser();
        return walletService.getWalletById(userAccount.getUsername(), id);
    }

    private UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
