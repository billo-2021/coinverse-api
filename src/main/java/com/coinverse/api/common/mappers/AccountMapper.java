package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.Account;
import com.coinverse.api.common.entities.AccountStatus;
import com.coinverse.api.common.entities.AccountToken;
import com.coinverse.api.common.entities.AccountTokenType;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.*;
import jakarta.annotation.Nullable;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {
    public abstract AccountResponse accountToAccountResponse(Account account);
    protected AccountStatusEnum accountStatusToAccountStatusEnum(AccountStatus accountStatus) {
        if (accountStatus == null) {
            return null;
        }

        return AccountStatusEnum.of(accountStatus.getName())
                .orElseThrow(() ->
                        new InvalidRequestException("Invalid status name '" + accountStatus.getName() + "'")
                );
    }

    public abstract AccountTokenResponse accountTokenToAccountTokenResponse(AccountToken accountToken);

    public abstract AccountTokenUpdateRequest accountTokenRequestToAccountTokenUpdateRequest(
            AccountTokenRequest accountTokenRequest);

    @Nullable
    protected AccountTokenTypeEnum accountTokenTypeToAccountTokenTypeEnum(AccountTokenType accountTokenType) {
        if (accountTokenType == null) {
            return null;
        }

        return AccountTokenTypeEnum
                .of(accountTokenType.getName())
                .orElseThrow(() ->
                        new MappingException("Invalid account verification method '" + accountTokenType.getName() + "'")
                );
    }
}
