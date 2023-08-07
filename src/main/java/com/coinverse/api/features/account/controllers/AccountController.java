package com.coinverse.api.features.account.controllers;

import com.coinverse.api.common.config.routes.AccountRoutes;
import com.coinverse.api.common.constants.ApiMessage;
import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserAccountEventResponse;
import com.coinverse.api.common.models.UserAccountEventTypeEnum;
import com.coinverse.api.common.services.UserAccountEventService;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.account.models.UpdatePasswordRequest;
import com.coinverse.api.features.account.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AccountRoutes.PATH)
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final UserAccountEventService userAccountEventService;

    @PatchMapping(AccountRoutes.CHANGE_PASSWORD)
    ApiMessageResponse changePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        accountService.updatePassword(updatePasswordRequest);
        userAccountEventService.addEvent(UserAccountEventTypeEnum.PASSWORD_UPDATE);
        return ApiMessageResponse.of(ApiMessage.USER_ACCOUNT_PASSWORD_UPDATED);
    }

    @GetMapping(AccountRoutes.ACTIVITY)
    PageResponse<UserAccountEventResponse> getAccountActivity(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                                              @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                                              @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                              @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);
        return accountService.getAccountActivity(pageable);
    }
}
