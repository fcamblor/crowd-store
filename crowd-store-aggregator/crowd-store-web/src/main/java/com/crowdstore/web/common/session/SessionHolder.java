package com.crowdstore.web.common.session;

import com.crowdstore.models.users.AuthenticatedUser;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fcamblor
 *         Utility class to centralize session scoped bean setters/getters
 */
public class SessionHolder {

    public static final String AUTHENTICATED_USER_KEY = SessionHolder.class.getName() + ".AUTHENTICATED_USER";

    public static void setAuthenticatedUser(HttpServletRequest request, HttpServletResponse response, AuthenticatedUser authenticatedUser) {
        request.getSession().setAttribute(AUTHENTICATED_USER_KEY, authenticatedUser);
        // Updating current locale...
        if (authenticatedUser != null) {
            updateLocale(request, response);
        }
    }

    public static AuthenticatedUser getAuthenticatedUser(HttpServletRequest request) {
        AuthenticatedUser authenticatedUser = (AuthenticatedUser) request.getSession().getAttribute(AUTHENTICATED_USER_KEY);
        return authenticatedUser;
    }

    public static void updateLocale(HttpServletRequest request, HttpServletResponse response) {
        AuthenticatedUser currentUser = getAuthenticatedUser(request);
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
        if (currentUser == null) {
            localeResolver.setLocale(request, response, null);
        } else {
            // Here is the only key point in the app where calling currentUser.getLocale() should be allowed...
            localeResolver.setLocale(request, response, currentUser.getLocale());
        }
    }
}