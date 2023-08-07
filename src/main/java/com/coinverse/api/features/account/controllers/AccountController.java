package com.coinverse.api.features.account.controllers;

import com.coinverse.api.common.constants.PageConstants;
import com.coinverse.api.common.models.ApiMessageResponse;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.UserAccountEventResponse;
import com.coinverse.api.common.services.DeviceResolutionService;
import com.coinverse.api.common.utils.RequestUtil;
import com.coinverse.api.common.validators.PageRequestValidator;
import com.coinverse.api.features.account.models.UpdatePasswordRequest;
import com.coinverse.api.features.account.services.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(AccountController.PATH)
@RequiredArgsConstructor
public class AccountController {
    public static final String PATH = "/api/v1/account";
    private final AccountService accountService;
    private final DeviceResolutionService deviceResolutionService;

    @PatchMapping("/change-password")
    ApiMessageResponse changePassword(@Valid @RequestBody final UpdatePasswordRequest updatePasswordRequest,
                                      final HttpServletRequest request) {
        final String deviceDetails = updatePasswordRequest.getDeviceDetails();;
        final String ipAddress = updatePasswordRequest.getIpAddress();

        if (Objects.isNull(deviceDetails) || deviceDetails.trim().isEmpty()) {
            final String userAgentHeader = request.getHeader("user-agent");

            updatePasswordRequest.setDeviceDetails(deviceResolutionService.getDeviceDetails(userAgentHeader));
        }

        if (Objects.isNull(ipAddress) || ipAddress.trim().isEmpty()) {
            updatePasswordRequest.setIpAddress(RequestUtil.extractClientIp(request));
        }

        accountService.updatePassword(updatePasswordRequest);
        return new ApiMessageResponse("User account password updated");
    }

    @GetMapping("/activity")
    PageResponse<UserAccountEventResponse> getAccountActivity(@RequestParam(value = "pageNumber", defaultValue = PageConstants.DEFAULT_PAGE, required = false) int pageNumber,
                                                                              @RequestParam(value = "pageSize", defaultValue = PageConstants.DEFAULT_SIZE, required = false) int pageSize,
                                                                              @RequestParam(value = "sortBy", defaultValue = PageConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                                                              @RequestParam(value = "sortDirection", defaultValue = PageConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {
        final Pageable pageable = PageRequestValidator.validate(pageNumber, pageSize, sortBy, sortDirection);
        return accountService.getAccountActivity(pageable);
    }
}
