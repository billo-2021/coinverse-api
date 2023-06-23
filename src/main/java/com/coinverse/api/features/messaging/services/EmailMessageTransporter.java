package com.coinverse.api.features.messaging.services;

import com.coinverse.api.features.messaging.models.SimpleMessage;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailMessageTransporter implements MessageTransporter<SimpleMessage> {
    private static final Logger logger = LoggerFactory.getLogger(EmailMessageTransporter.class);

    @Override
    public void transmit(@NotNull final SimpleMessage message) {
        logger.info("Sending message : {}, via email", message.getContent());
    }
}
