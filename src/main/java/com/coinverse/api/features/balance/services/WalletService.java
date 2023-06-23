package com.coinverse.api.features.balance.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.features.balance.mappers.WalletMapper;
import com.coinverse.api.features.balance.models.WalletResponse;
import com.coinverse.api.features.balance.validators.WalletValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final CryptoCurrencyExchangeRateRepository exchangeRateRepository;
    private final CryptoCurrencyPairRepository currencyPairRepository;
    private final CryptoCurrencyRepository currencyRepository;
    private final UserRepository userRepository;

    private final WalletValidator walletValidator;
    private final WalletMapper walletMapper;

    PageResponse<WalletResponse> getBalances(@NotNull final String username) {
        final Account account = walletValidator.validateWalletUsername(username);
        final PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);

        final Page<Wallet> walletPage = walletRepository.findByAccountId(account.getId(), pageRequest);
        final Page<WalletResponse> walletResponsePage = walletPage.map(walletMapper::waletToWalletResponse);

        return PageResponse.of(walletResponsePage);
    }

    WalletResponse getBalancesByWalletId(@NotNull final String username, @NotNull final Long walletId) {
        final Account account = walletValidator.validateWalletUsername(username);
        final User user = userRepository.findByAccountId(account.getId())
                .orElseThrow();

        final Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(InvalidRequestException::new);

        return walletMapper.waletToWalletResponse(wallet);

        // final BigDecimal walletBalance = wallet.getBalance();
//        final CryptoCurrency walletCurrency = wallet.getCurrency();
//
//        final CryptoCurrency defaultCurrency = currencyRepository.findByCode("usdt")
//                .orElseThrow(InvalidRequestException::new);
//
//        final CryptoCurrencyPair currencyPair = currencyPairRepository.findByCurrencyCodeCombination(defaultCurrency.getCode(),
//                walletCurrency.getCode())
//                .orElseThrow();
//
//        final CryptoCurrency baseCurrency = currencyPair.getBaseCurrency();
//        final CryptoCurrency quoteCurrency = currencyPair.getQuoteCurrency();
//
//        final CryptoCurrencyExchangeRate exchangeRate = exchangeRateRepository.findFirstByCurrencyPairId(currencyPair.getId())
//                .orElseThrow();
//
//        if (walletCurrency.getId().equals(baseCurrency.getId())) {
//
//        }
    }
}
