package com.coinverse.api.core.utils;

import java.security.SecureRandom;

public class RandomUtil {
    public static Integer nextInt(int lowerBound, int upperBound) {
        final SecureRandom random = new SecureRandom();
        return random.nextInt(lowerBound, upperBound - 1);
    }

    public static Integer nextIntKey(int keyLength) {
        final SecureRandom random = new SecureRandom();

        int lowerBound = (int) Math.pow(10, keyLength - 1);
        int bound = (int) Math.pow(10, keyLength);

        return random.nextInt(lowerBound, bound);
    }
}
