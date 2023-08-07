package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.*;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.exceptions.MappingException;
import com.coinverse.api.common.models.*;
import jakarta.annotation.Nullable;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class AccountMapper {

    @BeanMapping(builder = @Builder(disableBuilder = true))
    public abstract AccountResponse accountToAccountResponse(Account account);

    @Nullable
    protected AccountStatusEnum accountStatusToAccountStatusEnum(AccountStatus accountStatus) {
        if (accountStatus == null) {
            return null;
        }

        return AccountStatusEnum.of(accountStatus.getCode())
                .orElseThrow(() ->
                        new InvalidRequestException("Invalid status name '" + accountStatus.getName() + "'")
                );
    }

    protected Set<String> accountRolesToAccountRoleNames(Set<Role> roles) {
        return roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
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
                .of(accountTokenType.getCode())
                .orElseThrow(() ->
                        new MappingException("Invalid account verification method '" + accountTokenType.getName() + "'")
                );
    }

    @AfterMapping
    protected void addAccountIsVerified(Account account, @MappingTarget AccountResponse accountResponse) {
        final String accountStatusCode = account.getStatus().getCode();

        final AccountStatusEnum accountStatusEnum = AccountStatusEnum.of(accountStatusCode)
                .orElseThrow(() -> new MappingException("Invalid account status '" + accountResponse.getStatus().getName() + "'"));

        accountResponse.setIsVerified(accountStatusEnum == AccountStatusEnum.VERIFIED);
    }
}
