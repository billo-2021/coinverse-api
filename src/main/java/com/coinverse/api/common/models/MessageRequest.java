package com.coinverse.api.common.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MessageRequest {
    private Long accountId;
    private String subject;
    private String content;
}
