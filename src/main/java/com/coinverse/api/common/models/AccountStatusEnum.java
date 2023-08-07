package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum AccountStatusEnum {
    PENDING_VERIFICATION("pending_verification", "Pending Verification"),
    VERIFIED("verified", "Verified"),
    LOCKED("locked", "Locked"),
    CREDENTIALS_EXPIRED("credentials_expired", "Credentials Expired"),
    EXPIRED("expired", "Expired"),
    DELETED("deleted", "Deleted");

    private final String code;
    private final String name;

    private AccountStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }
    public String getName() {
        return name;
    }

    public static Optional<AccountStatusEnum> of(String code) {
        return Stream.of(values())
                .filter(accountStatusEnum ->
                        accountStatusEnum.getCode().equalsIgnoreCase(code))
                .findFirst();
    }
}
