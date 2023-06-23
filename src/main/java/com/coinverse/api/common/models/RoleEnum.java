package com.coinverse.api.common.models;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public enum RoleEnum {
    SYSTEM("system"),
    ADMIN("admin"),
    CUSTOMER("user");
    private final String authority;
    private RoleEnum(final @NotNull String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public static Optional<RoleEnum> of(final @NotNull String authority) {
        return Stream.of(values())
                .filter(roleEnum ->
                        roleEnum.getAuthority().equalsIgnoreCase(authority)
                ).findFirst();
    }
}
