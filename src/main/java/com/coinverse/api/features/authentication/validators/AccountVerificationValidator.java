package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.models.AccountStatusEnum;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.common.utils.ErrorMessageUtils;
import com.coinverse.api.features.authentication.models.TokenRequest;
import com.coinverse.api.features.authentication.models.TokenVerifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountVerificationValidator {
    private final AccountService accountService;

    public AccountResponse validateRequestToken(TokenRequest tokenRequest) {
       return validateAccountNotAlreadyVerified(tokenRequest.getUsername());
    }

    public AccountResponse validateVerifyAccount(TokenVerifyRequest tokenVerifyRequest) {
        return validateAccountNotAlreadyVerified(tokenVerifyRequest.getUsername());
    }

    private AccountResponse validateAccountNotAlreadyVerified(String username) {
        final AccountResponse accountResponse = accountService
                .getAccountByUsername(username)
                .orElseThrow(ErrorMessageUtils::getInvalidRequestException);

        if (accountResponse.getStatus() == AccountStatusEnum.VERIFIED) {
            throw ErrorMessageUtils.getInvalidRequestException();
        }

        return accountResponse;
    }
}
