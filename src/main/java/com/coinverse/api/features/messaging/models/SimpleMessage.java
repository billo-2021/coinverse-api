package com.coinverse.api.features.messaging.models;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class SimpleMessage extends Message {
    private String source;
    private String destination;

    public SimpleMessage(@NotNull final String source,
                         @NotNull final String destination,
                         @NotNull final String subject,
                         @NotNull final String content) {
        super(subject, content);
        this.source = source;
        this.destination = destination;
    }
}
