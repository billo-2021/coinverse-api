package com.coinverse.api.common.services;

import com.coinverse.api.common.models.MessageRequest;
import com.coinverse.api.common.models.MessageResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public Optional<MessageResponse> getMessageById(final @NotNull Long id) {
        return Optional.empty();
    }

    @Override
    public List<MessageResponse> getMessageByAccountId(final @NotNull Long accountId) {
        return null;
    }

    @Override
    public void addMessage(final @NotNull Long accountId, final @NotNull MessageRequest messageRequest) {

    }

    @Override
    public void deleteMessage(final @NotNull Long id) {

    }
}
