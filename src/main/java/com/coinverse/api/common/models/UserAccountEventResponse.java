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
public class UserAccountEventResponse {
    private EventResponse event;
    private String deviceDetails;
    private String ipAddress;
    private OffsetDateTime createdAt;
}
