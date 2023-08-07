package com.coinverse.api.features.balance.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.balance.models.WalletResponse;
import org.springframework.data.domain.Pageable;

public interface BalanceService {
    PageResponse<WalletResponse> getBalances(Pageable pageable);
    PageResponse<WalletResponse> getAllBalances();
    WalletResponse getBalancesByWalletId(final Long id);
}
