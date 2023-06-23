package com.coinverse.api.features.profile.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonPropertyOrder({
        "phoneNumber", "preference"
})
public class ProfileUpdateRequest {
    private String phoneNumber;
    private ProfilePreferenceUpdateRequest preference;
}
