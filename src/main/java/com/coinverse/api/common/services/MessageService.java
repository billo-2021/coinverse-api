package com.coinverse.api.common.services;

import com.coinverse.api.common.models.MessageRequest;
import com.coinverse.api.common.models.MessageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface MessageService {
    Optional<MessageResponse> getMessageById(final @NotNull Long id);
    List<MessageResponse> getMessageByAccountId(final @NotNull Long accountId);
    void addMessage(final @NotNull Long accountId, final @NotNull MessageRequest messageRequest);
    void deleteMessage(final @NotNull Long id);
}
