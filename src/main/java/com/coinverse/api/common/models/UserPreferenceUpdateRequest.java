package com.coinverse.api.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserPreferenceUpdateRequest {
    String currencyCode;
    Set<String> notificationMethods;
}
