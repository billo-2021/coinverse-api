package com.coinverse.api.features.administration.controllers;

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
@RequestMapping(AdministrationController.PATH)
@RequiredArgsConstructor
public class AdministrationController {
    public static final String PATH = "/api/v1/administration";

    private final AdministrationService administrationService;

    @GetMapping("/users")
    PageResponse<UserResponse> getUsers(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                        @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                        @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);
        return administrationService.getUsers(pageable);
    }

    @PostMapping("/users")
    ResponseEntity<ApiMessageResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        administrationService.addUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiMessageResponse("User added"));
    }

    @PatchMapping("/users/{username}/disable-account")
    public ApiMessageResponse disableUserAccount(@PathVariable String username) {
        administrationService.disableUserAccount(username);
        return new ApiMessageResponse("Account account disabled successfully");
    }

    @PatchMapping("/users/{username}/enable-account")
    public ApiMessageResponse enableUserAccount(@PathVariable String username) {
        administrationService.enableUserAccount(username);
        return new ApiMessageResponse("Account account enabled successfully");
    }

    @PostMapping("/crypto")
    public ResponseEntity<CryptoCurrencyResponse> addNewCryptoCurrency(@Valid @RequestBody
                                                                           final CryptoCurrencyRequest cryptoCurrencyRequest) {
        final CryptoCurrencyResponse cryptoCurrency = administrationService.addCryptoCurrency(cryptoCurrencyRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(cryptoCurrency);
    }

    @PatchMapping("/crypto/{currencyCode}")
    public ResponseEntity<CryptoCurrencyResponse> updateCryptoCurrency(@PathVariable String currencyCode,
                                                                       @Valid @RequestBody
                                                                           final CryptoCurrencyUpdateRequest cryptoCurrencyUpdateRequest) {
        final CryptoCurrencyResponse cryptoCurrency = administrationService.updateCryptoCurrency(currencyCode, cryptoCurrencyUpdateRequest);
        return ResponseEntity.ok(cryptoCurrency);
    }
}
