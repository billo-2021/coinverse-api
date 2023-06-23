package com.coinverse.api.features.trade.services;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.Wallet;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.repositories.WalletRepository;
import com.coinverse.api.features.trade.mappers.WalletMapper;
import com.coinverse.api.features.trade.models.WalletResponse;
import com.coinverse.api.features.trade.validators.WalletValidator;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    private final WalletValidator walletValidator;
    private final WalletMapper walletMapper;
    public PageResponse<WalletResponse> getWallets(@NotNull final String username, @NotNull final PageRequest pageRequest) {
        final Account userAccount = walletValidator.validateWalletUsername(username);

        final Page<Wallet> walletPage = walletRepository.findByAccountId(userAccount.getId(), pageRequest);
        final Page<WalletResponse> walletResponsePage = walletPage.map(walletMapper::walletToWalletResponse);

        return PageResponse.of(walletResponsePage);
    }

    public PageResponse<WalletResponse> getAllWallets(@NotNull final String username) {
        final Account userAccount = walletValidator.validateWalletUsername(username);

        final Page<Wallet> walletPage = walletRepository.findByAccountId(userAccount.getId(),
                PageRequest.of(0, Integer.MAX_VALUE));

        final Page<WalletResponse> walletResponsePage = walletPage.map(walletMapper::walletToWalletResponse);

        return PageResponse.of(walletResponsePage);
    }

    public WalletResponse getWalletById(@NotNull final String username, @NotNull final Long id) {
        final Account userAccount = walletValidator.validateWalletUsername(username);

        final Wallet wallet = walletRepository.findById(id)
                .orElseThrow(InvalidRequestException::new);

        final Account walletAccount = wallet.getAccount();
        final Long walletAccountId = walletAccount.getId();

        if (!userAccount.getId().equals(walletAccountId)) {
            throw new InvalidRequestException();
        }

        return walletMapper.walletToWalletResponse(wallet);
    }
}
