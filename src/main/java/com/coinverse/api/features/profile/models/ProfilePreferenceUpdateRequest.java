package com.coinverse.api.features.profile.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        "currencyCode", "notificationMethods"
})
public class ProfilePreferenceUpdateRequest {
    private String currencyCode;
    private Set<String> notificationMethods;
}
