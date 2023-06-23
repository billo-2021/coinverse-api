package com.coinverse.api.features.messaging.services;

import com.coinverse.api.common.models.UserResponse;
import com.coinverse.api.common.services.MessageService;
import com.coinverse.api.common.services.UserService;
import com.coinverse.api.features.messaging.config.MessagingProperties;
import com.coinverse.api.features.messaging.models.Message;
import com.coinverse.api.features.messaging.models.MessagingChannel;
import com.coinverse.api.features.messaging.models.SimpleMessage;
import jakarta.validation.constraints.NotNull;
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
    public void sendMessage(@NotNull final Long accountId,
                            @NotNull final Message message,
                            @NotNull final Set<MessagingChannel> messagingChannels) {
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

    private SimpleMessage getSimpleMessage(@NotNull final Message message,
                                           @NotNull final String source,
                                           @NotNull final String destination) {
        return SimpleMessage.builder()
                .subject(message.getSubject())
                .content(message.getContent())
                .source(source)
                .destination(destination)
                .build();
    }
}
