package com.coinverse.api.features.messaging.models;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SimpleMessage extends Message {
    private String source;
    private String destination;

    public SimpleMessage(String source,
                         String destination,
                         String subject,
                         String content) {
        super(subject, content);
        this.source = source;
        this.destination = destination;
    }
}
