package com.coinverse.api.features.authentication.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class PasswordTokenUserResponse {
    private String username;
    private String emailAddress;
}
