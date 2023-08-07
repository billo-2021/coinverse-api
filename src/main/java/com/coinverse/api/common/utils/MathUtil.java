package com.coinverse.api.common.utils;

import java.math.MathContext;
import java.math.RoundingMode;

public class MathUtil {
    public final static MathContext MATH_CONTEXT = new MathContext(16, RoundingMode.HALF_EVEN);
    public final static MathContext ROUNDING_CONTEXT = new MathContext(8, RoundingMode.HALF_UP);
}
