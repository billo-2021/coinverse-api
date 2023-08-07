package com.coinverse.api.common.services;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua_parser.Client;
import ua_parser.Parser;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DeviceResolutionService {
    private final Parser parser;

    @Nullable
    public String getDeviceDetails(final String agentString) {
        String deviceDetails = null;

        Client client = parser.parse(agentString);

        if (!Objects.isNull(client)) {
            final StringBuilder stringBuilder = new StringBuilder();

            final String userAgentFamily = client.userAgent.family;

            if (!Objects.isNull(userAgentFamily) && !userAgentFamily.trim().isEmpty()) {
                stringBuilder.append(userAgentFamily);
            }

            final String userAgentMajor = client.userAgent.major;

            if (!Objects.isNull(userAgentMajor) && !userAgentMajor.trim().isEmpty()) {
                stringBuilder.append(" ").append(userAgentMajor);
            }

            final String userAgentMinor = client.userAgent.minor;

            if (!Objects.isNull(userAgentMinor) && !userAgentMinor.trim().isEmpty()) {
                stringBuilder.append(".").append(userAgentMajor);
            }

            stringBuilder.append(" - ");

            final String osFamily = client.userAgent.minor;

            if (!Objects.isNull(osFamily) && !osFamily.trim().isEmpty()) {
                stringBuilder.append(osFamily);
            }

            final String osMajor = client.userAgent.minor;

            if (!Objects.isNull(osMajor) && !osMajor.trim().isEmpty()) {
                stringBuilder.append(" ").append(osMajor);
            }

            final String osMinor = client.userAgent.minor;

            if (!Objects.isNull(osMinor) && !osMinor.trim().isEmpty()) {
                stringBuilder.append(".").append(osMinor);
            }

            deviceDetails = stringBuilder.toString();
        }

        return deviceDetails;
    }
}
