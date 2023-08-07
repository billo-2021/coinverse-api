package com.coinverse.api.features.administration.controllers;

import com.coinverse.api.common.config.routes.AdministrationRoutes;
import com.coinverse.api.common.constants.ApiMessage;
import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserRequest;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.administration.models.CryptoCurrencyRequest;
import com.coinverse.api.features.administration.models.CryptoCurrencyResponse;
import com.coinverse.api.features.administration.models.CryptoCurrencyUpdateRequest;
import com.coinverse.api.features.administration.models.UserResponse;
import com.coinverse.api.features.administration.services.AdministrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AdministrationRoutes.PATH)
@RequiredArgsConstructor
public class AdministrationController {
    private final AdministrationService administrationService;

    @GetMapping(AdministrationRoutes.USERS)
    PageResponse<UserResponse> getUsers(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);
        return administrationService.getUsers(pageable);
    }

    @PostMapping(AdministrationRoutes.USERS)
    ResponseEntity<ApiMessageResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        administrationService.addUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiMessageResponse.of(ApiMessage.USER_ADDED));
    }

    @PatchMapping(AdministrationRoutes.DISABLE_USER_ACCOUNT)
    public ApiMessageResponse disableUserAccount(@PathVariable String username) {
        administrationService.disableUserAccount(username);
        return ApiMessageResponse.of(ApiMessage.ACCOUNT_DISABLED_SUCCESS);
    }

    @PatchMapping(AdministrationRoutes.ENABLE_USER_ACCOUNT)
    public ApiMessageResponse enableUserAccount(@PathVariable String username) {
        administrationService.enableUserAccount(username);
        return ApiMessageResponse.of(ApiMessage.ACCOUNT_ENABLED_SUCCESS);
    }

    @PostMapping(AdministrationRoutes.CRYPTO)
    public ResponseEntity<CryptoCurrencyResponse> addNewCryptoCurrency(@Valid @RequestBody
                                                                           CryptoCurrencyRequest cryptoCurrencyRequest) {
        final CryptoCurrencyResponse cryptoCurrency = administrationService.addCryptoCurrency(cryptoCurrencyRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cryptoCurrency);
    }

    @PatchMapping(AdministrationRoutes.UPDATE_CRYPTO_CURRENCY)
    public CryptoCurrencyResponse updateCryptoCurrency(@PathVariable String currencyCode,
                                                                       @Valid @RequestBody
                                                                           CryptoCurrencyUpdateRequest cryptoCurrencyUpdateRequest) {
        return administrationService.updateCryptoCurrency(currencyCode, cryptoCurrencyUpdateRequest);
    }
}
