package com.coinverse.api.features.messaging.services;

import com.coinverse.api.features.messaging.models.Message;
import com.coinverse.api.features.messaging.models.MessagingChannel;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface MessagingService {
    void sendMessage(@NotNull final Long accountId,
                     @NotNull final Message message,
                     @NotNull final Set<MessagingChannel> channels);
}
