package com.coinverse.api.features.administration.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Set;

@AllArgsConstructor
@Data
@Builder
public class AccountResponse {
    private String username;
    private Set<String> roles;
    private String status;
    private Boolean isEnabled;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
