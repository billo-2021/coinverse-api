package com.coinverse.api.common.models.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
@JsonPropertyOrder({"id", "code", "name"})
public class NotificationMethodResponse {
    private Long id;
    private String code;
    private String name;
}
