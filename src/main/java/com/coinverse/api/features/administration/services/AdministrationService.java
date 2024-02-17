package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.features.administration.models.*;
import org.springframework.data.domain.Pageable;

public interface AdministrationService {
    PageResponse<UserResponse> getUsers(Pageable pageable);
    void updateUserAccountEnabled(UpdateAccountEnabledRequest updateAccountEnabledRequest);

    void addUser(UserRequest userRequest);
    CryptoCurrencyResponse addCryptoCurrency(CryptoCurrencyRequest cryptoCurrencyRequest);
    CryptoCurrencyResponse updateCryptoCurrency(String currencyCode, CryptoCurrencyUpdateRequest cryptoCurrencyUpdateRequest);
}
