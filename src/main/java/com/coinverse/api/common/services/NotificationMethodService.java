package com.coinverse.api.common.services;

import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.exceptions.InvalidRequestException;
import com.coinverse.api.common.mappers.NotificationMethodMapper;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.NotificationMethodResponse;
import com.coinverse.api.common.repositories.NotificationChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationMethodService {
    private final NotificationChannelRepository notificationChannelRepository;
    private final NotificationMethodMapper notificationMethodMapper;

    public List<NotificationMethodResponse> findAll() {
        List<NotificationChannel> notificationChannels = notificationChannelRepository.findAll();
        return notificationMethodMapper.notificationChannelsToNotificationMethods(notificationChannels);
    }

    public PageResponse<NotificationMethodResponse> findAll(Pageable pageable) {
         Page<NotificationChannel> notificationChannelPage = notificationChannelRepository.findAll(pageable);

        return notificationMethodMapper.notificationChannelPageToNotificationResponsePage(notificationChannelPage);
    }

    public NotificationMethodResponse findByCode(String code) {
        NotificationChannel notificationChannel = notificationChannelRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new InvalidRequestException("Invalid code '" + code + "'"));

        return notificationMethodMapper.notificationChannelToNotificationMethodResponse(notificationChannel);
    }
}
