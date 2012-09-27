package com.crowdstore.common.threads;

import com.crowdstore.common.exceptions.Exceptions;
import com.crowdstore.models.context.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * @author fcamblor
 *         Utility class which will welcome every ThreadLocal-related objects and provide
 *         utility methods to transmit these objects from one thread to another
 */
public class AppThreadedInfos {
    private static final Logger LOG = LoggerFactory.getLogger(AppThreadedInfos.class);

    private static ThreadLocal<AppContext> appContextInstance = new ThreadLocal<AppContext>();
    private static ThreadLocal<MessageSource> messageSourceInstance = new ThreadLocal<MessageSource>();

    /**
     * Simple shortcut for LocaleContextHolder.getLocale()
     */
    public static Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * Utility method used to generate a context which will be transferable to an async call
     */
    public static Context createAppThreadedContext() {
        AppContext currentAppContext = getAppContext();
        return Context.create(
                currentAppContext == null ? null : currentAppContext.clone(),
                getMessageSource());
    }

    public static void provideAppThreadedContextToCurrentThread(Context context) {
        setAppContext(context.getAppContext());
        setMessageSource(context.getMessageSource());
        if (context.getAppContext() != null) {
            LocaleContextHolder.setLocale(context.getAppContext().getCurrentUserLocale());
        } else {
            // else : no need to set any locale since if AppContext is null, it means we are
            // "outside" a http request (= in a batch task) => we should deliberately not set any locale
            // because locale should be set "manually" with Locales.withLocale({....})
            LocaleContextHolder.setLocale(null);
        }
    }

    /**
     * Utility method to provide transfered Context to current thread objects and execute a callback method
     */
    public static void provideAppThreadedContextToCurrentThread(Context context, Callable<Void> callable) {
        Context initialContext = createAppThreadedContext();
        provideAppThreadedContextToCurrentThread(context);
        try {
            callable.call();
        } catch (Throwable t) {
            LOG.error(String.format("Exception thrown during async call started near :%n%s",
                    Exceptions.formatStackTrace(context.getCreationStackTrace())), t);
        } finally {
            provideAppThreadedContextToCurrentThread(initialContext);
        }
    }

    protected static void setMessageSource(MessageSource messageSource) {
        messageSourceInstance.set(messageSource);
    }

    public static MessageSource getMessageSource() {
        return messageSourceInstance.get();
    }

    protected static void setAppContext(AppContext appContext) {
        appContextInstance.set(appContext);
    }

    public static AppContext getAppContext() {
        return appContextInstance.get();
    }

    public static class Context {
        AppContext appContext;
        MessageSource messageSource;
        StackTraceElement[] creationStackTrace; // Useful stacktrace to see "where" the context has been created !

        private Context(AppContext appContext, MessageSource messageSource) {
            this.creationStackTrace = new Exception().getStackTrace();
            this.appContext = appContext;
            this.messageSource = messageSource;
        }

        public AppContext getAppContext() {
            return appContext;
        }

        public MessageSource getMessageSource() {
            return messageSource;
        }

        public StackTraceElement[] getCreationStackTrace() {
            return creationStackTrace;
        }

        public static Context create(AppContext appContext, MessageSource messageSource) {
            return new Context(appContext, messageSource);
        }
    }
}