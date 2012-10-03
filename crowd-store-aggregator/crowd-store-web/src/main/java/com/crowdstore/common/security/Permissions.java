package com.crowdstore.common.security;

import com.crowdstore.models.security.ConditionnalOperator;
import com.crowdstore.models.security.GlobalAuthorization;
import com.crowdstore.models.users.AuthenticatedUser;

/**
 * @author fcamblor
 */
public class Permissions {
    public static boolean hasAuthorization(AuthenticatedUser user, GlobalAuthorization authorization) {
        return user == null ? false : user.hasGlobalAuthorization(authorization);
    }

    public static void ensureHasAuthorization(AuthenticatedUser user, GlobalAuthorization authorization) throws RestrictedFeatureException {
        ensureHasAuthorizations(user, authorization);
    }

    public static boolean hasAuthorizations(AuthenticatedUser user, GlobalAuthorization... authorizations) {
        return hasAuthorizations(user, ConditionnalOperator.AND, authorizations);
    }

    public static void ensureHasAuthorizations(AuthenticatedUser user, GlobalAuthorization... authorizations) throws RestrictedFeatureException {
        ensureHasAuthorizations(user, ConditionnalOperator.AND, authorizations);
    }

    public static boolean hasAuthorizations(AuthenticatedUser user, ConditionnalOperator operator, GlobalAuthorization... authorizations) {
        if (authorizations.length == 0) {
            return user != null; // This is deliberate, and imply that @RequireGlobalAuthorizations() will return true only if user is not null
        }
        boolean hasAuthorization = hasAuthorization(user, authorizations[0]);
        for (int i = 0; i < authorizations.length; i++) {
            hasAuthorization = operator.apply(hasAuthorization, hasAuthorization(user, authorizations[i]));
        }
        return hasAuthorization;
    }

    public static void ensureHasAuthorizations(AuthenticatedUser user, ConditionnalOperator operator, GlobalAuthorization... authorizations)
            throws RestrictedFeatureException {
        if (!hasAuthorizations(user, operator, authorizations)) {
            throw new RestrictedFeatureException(authorizations, operator);
        }
    }
}
