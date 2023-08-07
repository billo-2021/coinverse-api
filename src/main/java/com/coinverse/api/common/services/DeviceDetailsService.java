package com.coinverse.api.common.services;

import com.coinverse.api.common.constants.HeaderConstants;
import com.coinverse.api.common.models.DeviceInformation;
import com.coinverse.api.common.utils.RequestUtil;
import com.coinverse.api.common.utils.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeviceDetailsService {
    private final HttpServletRequest request;
    private final Parser parser;
    public DeviceInformation getDeviceInfo() {
        final String ipAddress = RequestUtil.extractClientIp(request);
        String deviceDetails = request.getHeader(HeaderConstants.FROM);

        if (StringUtils.isEmptyOrNull(deviceDetails)) {
            deviceDetails = getDeviceDetailsFromHeader(request);
        }

        return DeviceInformation
                .builder()
                .ipAddress(ipAddress)
                .details(deviceDetails)
                .build();
    }

    private String getDeviceDetailsFromHeader(HttpServletRequest request) {
        final String userAgentString = request.getHeader(HeaderConstants.USER_AGENT);
        final Client client = parser.parse(userAgentString);
        return parseDeviceDetails(userAgentString, client);
    }

    private String parseDeviceDetails(String agentString, Client client) {
        final StringBuilder stringBuilder = new StringBuilder();

        if (Objects.isNull(client)) {
            return stringBuilder.toString();
        }

        final String userAgentFamily = client.userAgent.family;

        if (StringUtils.isNotBlank(agentString)) {
            stringBuilder.append(userAgentFamily);
        }

        final String userAgentMajor = client.userAgent.major;
        if (StringUtils.isNotBlank(userAgentMajor)) {
            stringBuilder.append(" ").append(userAgentMajor);
        }

        final String userAgentMinor = client.userAgent.minor;
        if (!StringUtils.isNotBlank(userAgentMinor)) {
            stringBuilder.append(".").append(userAgentMajor);
        }

        stringBuilder.append(" - ");

        final String osFamily = client.userAgent.minor;
        if (StringUtils.isNotBlank(osFamily)) {
            stringBuilder.append(osFamily);
        }

        final String osMajor = client.userAgent.minor;

        if (StringUtils.isNotBlank(osMajor)) {
            stringBuilder.append(" ").append(osMajor);
        }

        final String osMinor = client.userAgent.minor;
        if (StringUtils.isNotBlank(osMinor)) {
            stringBuilder.append(".").append(osMinor);
        }

        return stringBuilder.toString();
    }
}
