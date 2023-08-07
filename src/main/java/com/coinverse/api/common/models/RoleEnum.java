package com.coinverse.api.common.models;

import java.util.Optional;
import java.util.stream.Stream;

public enum RoleEnum {
    SYSTEM("system", "System"),
    ADMIN("admin", "Admin"),
    CUSTOMER("customer", "Customer");
    private final String authority;
    private final String name;

    private RoleEnum(String authority, String name) {
        this.authority = authority;
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public String getName() { return name; }

    public static Optional<RoleEnum> of(String authority) {
        return Stream.of(values())
                .filter(roleEnum ->
                        roleEnum.getAuthority().equalsIgnoreCase(authority)
                ).findFirst();
    }
}
