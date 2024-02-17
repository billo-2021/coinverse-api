package com.coinverse.api.core.utils;

import java.math.BigDecimal;

import static com.coinverse.api.common.utils.MathUtil.MATH_CONTEXT;
import static com.coinverse.api.common.utils.MathUtil.ROUNDING_CONTEXT;

public class BigDecimalUtil {
    public final static BigDecimal ZERO = new BigDecimal("0.00000", MATH_CONTEXT);

    public static BigDecimal multiply(BigDecimal multiplicand, BigDecimal multiplier) {
        return multiplicand.multiply(multiplier, MATH_CONTEXT);
    }

    public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, MATH_CONTEXT);
    }

    public static BigDecimal subtract(BigDecimal minuend, BigDecimal subtrahend) {
        return minuend.subtract(subtrahend, MATH_CONTEXT);
    }

    public static BigDecimal add(BigDecimal augend, BigDecimal addend) {
        return augend.add(addend).round(ROUNDING_CONTEXT);
    }

    public static boolean isNegative(BigDecimal number) {
        return number.compareTo(ZERO) < 0;
    }
}
