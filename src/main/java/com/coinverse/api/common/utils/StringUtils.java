package com.coinverse.api.common.utils;

import java.util.Objects;

public class StringUtils {
    public static boolean isEmptyOrNull(String str) {
        return Objects.isNull(str) || str.trim().isEmpty();
    }

    public static boolean isNotBlank(String str) {
        return !isEmptyOrNull(str);
    }

    public static void appendString(StringBuilder stringBuilder, String str) {
        if (isNotBlank(str)) {
            stringBuilder.append(str);
        }
    }
}
