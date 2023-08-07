package com.coinverse.api.common.models;

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
public class UserPreferenceRequest {
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;

    @NotNull(message = "notifications is required")
    private Set<String> notificationMethods;
}
