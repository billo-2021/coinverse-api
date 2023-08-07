package com.coinverse.api.features.balance.services;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.*;
import com.coinverse.api.features.balance.mappers.WalletMapper;
import com.coinverse.api.features.balance.models.WalletResponse;
import com.coinverse.api.features.balance.validators.WalletValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    private final WalletValidator walletValidator;
    private final WalletMapper walletMapper;

    PageResponse<WalletResponse> getWallets(String username, Pageable pageable) {
        final Account userAccount = walletValidator.validateWalletUsername(username);

        final Page<Wallet> walletPage = walletRepository.findByAccountId(userAccount.getId(), pageable);
        final Page<WalletResponse> walletResponsePage = walletPage.map(walletMapper::walletToWalletResponse);

        return PageResponse.of(walletResponsePage);
    }

    public PageResponse<WalletResponse> getAllWallets(String username) {
        final Account userAccount = walletValidator.validateWalletUsername(username);

        final Page<Wallet> walletPage = walletRepository.findByAccountId(userAccount.getId(),
                PageRequest.of(0, Integer.MAX_VALUE));

        final Page<WalletResponse> walletResponsePage = walletPage.map(walletMapper::walletToWalletResponse);

        return PageResponse.of(walletResponsePage);
    }

    WalletResponse getWalletById(String username, Long walletId) {
        final Account userAccount = walletValidator.validateWalletUsername(username);

        final Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new InvalidRequestException("Invalid walletId"));

        final Account walletAccount = wallet.getAccount();
        final Long walletAccountId = walletAccount.getId();

        if (!userAccount.getId().equals(walletAccountId)) {
            throw new InvalidRequestException();
        }

        return walletMapper.walletToWalletResponse(wallet);
    }
}
