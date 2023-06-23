package com.coinverse.api.features.balance.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.security.models.UserAccount;
import com.coinverse.api.features.balance.models.WalletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {
    private final WalletService walletService;

    @Override
    public PageResponse<WalletResponse> getBalances() {
        final UserAccount currentUser = getCurrentUser();

        return walletService.getBalances(currentUser.getUsername());
    }

    @Override
    public WalletResponse getBalancesByWalletId(Long id) {
        final UserAccount currentUser = getCurrentUser();

        return walletService.getBalancesByWalletId(currentUser.getUsername(), id);
    }

    private UserAccount getCurrentUser() {
        return (UserAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }
}
