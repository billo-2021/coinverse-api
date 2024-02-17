package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.features.administration.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {
    private final UserManager userManager;
    private final UserAccountManager userAccountManager;
    private final CurrencyAdministrationService currencyAdministrationService;

    @Override
    public PageResponse<UserResponse> getUsers(Pageable pageable) {
        return userManager.getUsers(pageable);
    }

    @Override
    public void updateUserAccountEnabled(UpdateAccountEnabledRequest updateAccountEnabledRequest) {
        userAccountManager.updateAccountEnabled(updateAccountEnabledRequest);
    }

    @Override
    public void addUser(UserRequest userRequest) {
        userManager.addUser(userRequest);
    }

    @Override
    public CryptoCurrencyResponse addCryptoCurrency(CryptoCurrencyRequest cryptoCurrencyRequest) {
        return currencyAdministrationService.addCryptoCurrency(cryptoCurrencyRequest);
    }

    @Override
    public CryptoCurrencyResponse updateCryptoCurrency(String currencyCode, CryptoCurrencyUpdateRequest cryptoCurrencyUpdateRequest) {
        return currencyAdministrationService.updateCryptoCurrency(currencyCode, cryptoCurrencyUpdateRequest);
    }
}
