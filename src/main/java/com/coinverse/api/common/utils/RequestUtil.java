package com.coinverse.api.common.utils;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

public class RequestUtil {
    public static String extractClientIp(HttpServletRequest request) {
        String clientXForwardedForIp = request.getHeader("x-forwarded-for");

        return Objects.nonNull(clientXForwardedForIp) ?
                clientXForwardedForIp :
                request.getRemoteAddr();
    }
}
