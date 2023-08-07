package com.coinverse.api.features.administration.services;

import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.features.administration.models.CryptoCurrencyRequest;
import com.coinverse.api.features.administration.models.CryptoCurrencyResponse;
import com.coinverse.api.features.administration.models.CryptoCurrencyUpdateRequest;
import com.coinverse.api.features.administration.models.UserResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface AdministrationService {
    PageResponse<UserResponse> getUsers(Pageable pageable);
    void disableUserAccount(String username);
    void enableUserAccount(String username);

    void addUser(UserRequest userRequest);
    CryptoCurrencyResponse addCryptoCurrency(CryptoCurrencyRequest cryptoCurrencyRequest);
    CryptoCurrencyResponse updateCryptoCurrency(String currencyCode, CryptoCurrencyUpdateRequest cryptoCurrencyUpdateRequest);
}
