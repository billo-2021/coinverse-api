package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.models.*;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.trade.mappers.TradeMapper;
import com.coinverse.api.features.trade.models.CurrencyTransactionResponse;
import com.coinverse.api.features.trade.models.TradeRequest;
import com.coinverse.api.features.trade.validators.TradeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {
    private final CurrencyTransactionRepository currencyTransactionRepository;

    private final TradeProcessor tradeProcessor;
    private final TradeValidator tradeValidator;
    private final TradeMapper tradeMapper;

    @Transactional
    @Override
    public CurrencyTransactionResponse requestTrade(TradeRequest tradeRequest) {
        return tradeProcessor.requestTrade(tradeRequest);
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
                .orElseThrow(() -> ErrorMessageUtils.getInvalidRequestException("tradeId", String.valueOf(id)));

        final Account transactionSourceAccount = currencyTransaction.getSourceWallet().getAccount();
        final Long sourceAccountId = transactionSourceAccount.getId();

        Account transactionDestinationAccount = currencyTransaction.getDestinationWallet().getAccount();
        final Long destinationAccountId = transactionDestinationAccount.getId();

        if (!accountId.equals(sourceAccountId) || !accountId.equals(destinationAccountId)) {
            throw ErrorMessageUtils.getInvalidRequestException();
        }

        return tradeMapper.currencyTransactionToCurrencyTransactionResponse(currencyTransaction);
    }

    private UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
