package com.coinverse.api.common.services;

import com.coinverse.api.common.models.MessageRequest;
import com.coinverse.api.common.models.MessageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {
    Optional<MessageResponse> getMessageById(Long id);
    List<MessageResponse> getMessageByAccountId(Long accountId);
    void addMessage(Long accountId, MessageRequest messageRequest);
    void deleteMessage(Long id);
}
