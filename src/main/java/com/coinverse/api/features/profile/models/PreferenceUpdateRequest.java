package com.coinverse.api.features.profile.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PreferenceUpdateRequest {
    @NotBlank(message = "currencyCode is required")
    private String currencyCode;

    @NotNull(message = "notifications is required")
    private Set<String> notificationMethods;

    private String deviceDetails;
    private String ipAddress;
}
