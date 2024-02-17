package com.coinverse.api.features.messaging.services;

import com.coinverse.api.features.messaging.models.Message;
import org.springframework.stereotype.Service;

@Service
public interface MessageTransporter<T extends Message> {
    void transmit(final T message);
}
