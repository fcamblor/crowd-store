package com.crowdstore.models.users;

import com.crowdstore.models.role.GlobalRole;
import com.crowdstore.models.security.GlobalAuthorization;

import java.util.Locale;

/**
 * @author fcamblor
 */
public class AuthenticatedUser extends User {
    private Locale locale;
    private GlobalRole globalRole;

    public AuthenticatedUser(UserIdentity identity) {
        super(identity);
    }

    /**
     * @deprecated Use either LocaleContextHolder#getLocale() or AppThreadedInfos#getLocale() instead !
     *             This method should only be called when updating current locale in spring !
     *             Note : We cannot set an "accessibleLocale" flag+check here because we don't want to throw exception
     *             when serializing (in JSON) current user's locale. So the @deprecated flag is the better solution to avoid calling
     *             this method directly in code.
     */
    @Deprecated
    public Locale getLocale() {
        return locale;
    }

    public AuthenticatedUser setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public AuthenticatedUser setGlobalRole(GlobalRole _globalRole) {
        this.globalRole = _globalRole;
        return this;
    }

    public GlobalRole getGlobalRole() {
        return this.globalRole;
    }

    public boolean hasGlobalAuthorization(GlobalAuthorization authorization) {
        return globalRole.getAuthorizations().contains(authorization);
    }
}
