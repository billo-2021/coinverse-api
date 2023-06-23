package com.coinverse.api.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountTokenRequest {
    private String type;
    private String key;
    private OffsetDateTime expiresAt;
}
