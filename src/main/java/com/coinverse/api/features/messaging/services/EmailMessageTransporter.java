package com.coinverse.api.features.messaging.services;

import com.coinverse.api.features.messaging.models.SimpleMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailMessageTransporter implements MessageTransporter<SimpleMessage> {
    private final JavaMailSender emailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailMessageTransporter.class);

    @Override
    @Async
    public void transmit(SimpleMessage message) {
        logger.info("Sending message : {}, via email", message.getContent());
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(message.getSource());
        mailMessage.setTo(message.getDestination());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getContent());

        try {
            //this.emailSender.send(mailMessage);
        } catch (Exception ex) {
            logger.error("Exception while trying to send email");
        }
    }
}
