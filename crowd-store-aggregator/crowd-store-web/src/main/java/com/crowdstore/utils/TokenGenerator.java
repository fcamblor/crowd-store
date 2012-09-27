package com.crowdstore.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author damienriccio
 */
public final class TokenGenerator {
    private static SecureRandom random = new SecureRandom();

    private TokenGenerator() {
    }

    public static String generate() {
        return new BigInteger(130, random).toString(32);

    }
}
