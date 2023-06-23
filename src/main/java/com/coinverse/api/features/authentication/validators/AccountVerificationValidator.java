package com.coinverse.api.features.authentication.validators;

import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.models.AccountResponse;
import com.coinverse.api.common.models.AccountStatusEnum;
import com.coinverse.api.common.services.AccountService;
import com.coinverse.api.features.authentication.models.TokenRequest;
import com.coinverse.api.features.authentication.models.TokenVerifyRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountVerificationValidator {
    private final AccountService accountService;

    public AccountResponse validateRequestToken(@NotNull TokenRequest tokenRequest) {
       return validateAccountNotAlreadyVerified(tokenRequest.getUsername());
    }

    public AccountResponse validateVerifyAccount(@NotNull TokenVerifyRequest tokenVerifyRequest) {
        return validateAccountNotAlreadyVerified(tokenVerifyRequest.getUsername());
    }

    private AccountResponse validateAccountNotAlreadyVerified(@NotNull final String username) {
        final AccountResponse accountResponse = accountService
                .getAccountByUsername(username)
                .orElseThrow(InvalidRequestException::new);

        if (accountResponse.getStatus() == AccountStatusEnum.VERIFIED) {
            throw new InvalidRequestException();
        }

        return accountResponse;
    }
}
