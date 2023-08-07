package com.coinverse.api.common.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountRequest {
    @NotBlank(message = "username is required")
    @Email(message = "Invalid email")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    @NotNull(message = "roles is required")
    private Set<String> roles;
}
