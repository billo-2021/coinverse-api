package com.coinverse.api.features.authentication.models;

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
public class RegisterPreferenceRequest {
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;

    @NotNull(message = "notifications is required")
    private Set<String> notificationMethods;
}
