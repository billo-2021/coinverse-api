package com.coinverse.api.common.mappers;

import com.coinverse.api.common.entities.NotificationChannel;
import com.coinverse.api.common.models.PageResponse;
import com.coinverse.api.common.models.response.NotificationMethodResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMethodMapper {
    NotificationMethodResponse notificationChannelToNotificationMethodResponse(NotificationChannel notificationChannel);
    List<NotificationMethodResponse> notificationChannelsToNotificationMethods(List<NotificationChannel> notificationChannels);
    default PageResponse<NotificationMethodResponse> notificationChannelPageToNotificationResponsePage(Page<NotificationChannel> notificationChannelPage) {
        final Page<NotificationMethodResponse> notificationMethodResponsePage = notificationChannelPage.map(this::notificationChannelToNotificationMethodResponse);

        return PageResponse.of(notificationMethodResponsePage);
    }
}
