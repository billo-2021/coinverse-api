package com.coinverse.api.features.messaging.services;

import com.coinverse.api.features.messaging.models.Message;
import com.coinverse.api.features.messaging.models.MessagingChannelEnum;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface MessagingService {
    void sendMessage(Long accountId,
                     Message message,
                     Set<MessagingChannelEnum> channels);
}
