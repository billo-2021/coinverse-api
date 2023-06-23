package com.coinverse.api.features.messaging.services;

import com.coinverse.api.features.messaging.models.Message;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface MessageTransporter<T extends Message> {
    void transmit(@NotNull final T message);
}
