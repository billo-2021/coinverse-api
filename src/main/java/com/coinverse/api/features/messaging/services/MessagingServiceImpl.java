package com.coinverse.api.features.messaging.services;

import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.services.MessageService;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.messaging.config.MessagingProperties;
import com.coinverse.api.features.messaging.models.Message;
import com.coinverse.api.features.messaging.models.MessagingChannelEnum;
import com.coinverse.api.features.messaging.models.SimpleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {
    private final MessagingProperties messagingProperties;
    private final EmailMessageTransporter emailMessageTransporter;
    private final SmsMessageTransporter smsMessageTransporter;
    private final UserService userService;
    private final MessageService messageService;


    @Override
    public void sendMessage(Long accountId,
                            Message message,
                            Set<MessagingChannelEnum> messagingChannels) {
        final UserResponse userResponse = userService.getUserByAccountId(accountId).orElseThrow();

        messagingChannels.forEach(messagingChannel -> {
            switch(messagingChannel) {
                case EMAIL -> {
                    final SimpleMessage simpleMessage = getSimpleMessage(
                            message,
                            messagingProperties.emailSource(),
                            userResponse.getEmailAddress()
                    );
                    emailMessageTransporter.transmit(simpleMessage);
                }
                case SMS -> {
                    final SimpleMessage simpleMessage = getSimpleMessage(
                            message,
                            messagingProperties.smsSource(),
                            userResponse.getPhoneNumber()
                    );
                    smsMessageTransporter.transmit(simpleMessage);
                }
            }
        });
    }

    private SimpleMessage getSimpleMessage(Message message,
                                           String source,
                                           String destination) {
        return SimpleMessage.builder()
                .subject(message.getSubject())
                .content(message.getContent())
                .source(source)
                .destination(destination)
                .build();
    }
}
