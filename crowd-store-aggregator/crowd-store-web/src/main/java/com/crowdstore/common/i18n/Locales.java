package com.crowdstore.common.i18n;

import com.crowdstore.common.threads.AppThreadedInfos;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * @author fcamblor
 */
public class Locales {
    /**
     * Utility method allowing to execute a lambda expression with current locale
     * overwriten
     * This will be particularly useful to send localized emails to non authenticated user
     */
    public static void withLocale(Locale locale, Callable callable) {
        // Overriding current locale in LocaleContextHolder
        // We could call appContext.setLocale(locale) too, but appContext
        // can be null so we don't
        Locale initialLocale = AppThreadedInfos.getLocale();
        LocaleContextHolder.setLocale(locale);
        try {
            callable.call();
        } catch (Exception e) {
            throw new RuntimeException(String.format("Error during Locales.withLocale(%s) call", locale), e);
        } finally {
            LocaleContextHolder.setLocale(initialLocale);
        }
    }
}
