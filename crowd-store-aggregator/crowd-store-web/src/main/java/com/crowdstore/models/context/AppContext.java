package com.crowdstore.models.context;

import com.crowdstore.common.threads.AppThreadedInfos;
import com.crowdstore.models.users.AuthenticatedUser;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

/**
 * @author fcamblor
 *         Application context, thread scoped spring bean, allowing to be
 *         injected in every spring bean that will need it (services, repositories, controllers for instance)
 */
public class AppContext implements Serializable, Cloneable {
    // Allows to ensure user can be overwritten only in particular cases
    // By default, you shouldn't be allowed to update app context's user because it won't update
    // user stored in session.
    // Nevertheless, we need to have a setter in order to inject current user in application key points
    private boolean writableAuthenticatedUser = false;
    private AuthenticatedUser authenticatedUser;

    protected AppContext() {
        this(null);
    }

    public AppContext(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public AppContext setAuthenticatedUser(AuthenticatedUser authenticatedUser) {
        if (!writableAuthenticatedUser) {
            // If you encounter this issue, think twice before setting writableAuthenticatedUser to true
            // You should overwrite appContext's current user only in particular cases...
            throw new IllegalStateException("Trying to overwrite AppContext's authenticatedUser whereas writableAuthenticatedUser has not been set to true.." +
                    "Are you sure you're doing things right ?");
        }
        this.authenticatedUser = authenticatedUser;
        return this;
    }

    public AuthenticatedUser getAuthenticatedUser() {
        return this.authenticatedUser;
    }

    /**
     * @deprecated Use AppThreadedInfos.getLocale() instead !
     */
    @Deprecated
    public Locale getLocale() {
        // Note : AppContext.getLocale() implementation should _not_ be based on getAuthenticatedUser().getLocale() because in some
        // cases (like the Locales.withLocale() call) we don't _always_ want to rely on current user's locale
        // Note that LocaleContextHolder.setLocale() should be set to current user's locale by default
        // (see AppThreadedInfosRequestInterceptor)
        return AppThreadedInfos.getLocale();
    }

    public void setLocale(Locale newLocale) {
        LocaleContextHolder.setLocale(newLocale);
    }

    /**
     * Will return current user locale or, if there isn't any logged user, current locale in context holder
     * Note that this method should be rarely used (prefer getLocale() instead) : it should only be used when
     * current locale (retrieved via LocaleContextHolder.getLocale()) is not the same as getUser().getLocale()
     * (it happens when, for instance, you use Locales.withLocale()) and you want to retrieve current logged user
     * locale inside this particular context
     */
    public Locale getCurrentUserLocale() {
        if (getAuthenticatedUser() != null) {
            return getAuthenticatedUser().getLocale();
        } else {
            return AppThreadedInfos.getLocale();
        }
    }

    public void setWritableAuthenticatedUser(boolean writableAuthenticatedUser) {
        this.writableAuthenticatedUser = writableAuthenticatedUser;
    }

    @Override
    public AppContext clone() {
        return new AppContext(authenticatedUser);
    }
}
