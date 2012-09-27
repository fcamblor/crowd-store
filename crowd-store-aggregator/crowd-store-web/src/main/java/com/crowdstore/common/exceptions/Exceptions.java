package com.crowdstore.common.exceptions;

/**
 * @author fcamblor
 */
public class Exceptions {
    /**
     * Utility method allowing to throw IllegalArgumentException with String.format() message
     *
     * @throws IllegalArgumentException Always
     */
    public static void throwIAE(String formatString, Object... params) throws IllegalArgumentException {
        throw createIAE(formatString, params);
    }

    public static IllegalArgumentException createIAE(String formatString, Object... params) {
        return new IllegalArgumentException(String.format(formatString, params));
    }

    public static String formatStackTrace(StackTraceElement[] creationStackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : creationStackTrace) {
            sb.append(String.format("%s%n", stackTraceElement.toString()));
        }
        return sb.toString();
    }
}
