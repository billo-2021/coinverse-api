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
public class MessageResponse {
    private Long id;
    private String subject;
    private String content;
    private MessageStatusEnum status;
    private OffsetDateTime readAt;
}
