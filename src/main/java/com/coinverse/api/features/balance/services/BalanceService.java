package com.coinverse.api.features.balance.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.features.balance.models.WalletResponse;

public interface BalanceService {
    PageResponse<WalletResponse> getBalances();
    WalletResponse getBalancesByWalletId(final Long id);
}
