package com.coinverse.api.features.authentication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ResetPasswordTokenResponse {
    private String username;
    private String emailAddress;
}
