package com.crowdstore.restapi.common;

import com.crowdstore.models.context.AppContext;
import com.crowdstore.models.role.GlobalRole;
import com.crowdstore.models.security.GlobalAuthorization;
import com.crowdstore.models.security.StoreAuthorization;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.service.user.UserService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author fcamblor
 * Utility class that will fake the current user permission during a call
 */
@Component
public class PermissionFaker {

    @Inject
    AppContext appContext;

    @Inject
    UserService userService;

    private static class FullAuthorizationsUserAuthorizationResolver extends AuthenticatedUser.AuthorizationsResolver {
        @Override
        public List<GlobalAuthorization> resolveGlobalAuthorizationOf(AuthenticatedUser user) {
            return Arrays.asList(GlobalAuthorization.values());
        }

        @Override
        public List<StoreAuthorization> resolveStoreAuthorizationsOf(AuthenticatedUser user, String storeName) {
            return Arrays.asList(StoreAuthorization.values());
        }
    }

    /**
     * Allows to execute callback with an existing user
     * @param sudoer The sudoer we will use for this call
     * @param runnable The call we want to sudo
     */
    public void sudoExecute(AuthenticatedUser sudoer, Runnable runnable){

        // Hacking current app context temporarily ...
        AuthenticatedUser oldAuthenticatedUser = appContext.getAuthenticatedUser();

        appContext.setWritableAuthenticatedUser(true);
        appContext.setAuthenticatedUser(sudoer);
        appContext.setWritableAuthenticatedUser(false);
        try {
            // Executing callback
            runnable.run();
        }finally{
            // Setting back old authenticated user...
            appContext.setWritableAuthenticatedUser(true);
            appContext.setAuthenticatedUser(oldAuthenticatedUser);
            appContext.setWritableAuthenticatedUser(false);
        }
    }
    
    /**
     * Allows to execute callback with a non existing user having every possible rights
     * Beware : Sudoer, here, won't have any id. So, called code shouldn't rely on existing user id
     * for its role checking
     * @param runnable The call we want to sudo
     */
    public void executeWithAllRights(Runnable runnable){
        executeWithFakeRights(new FullAuthorizationsUserAuthorizationResolver(),  runnable);
    }

    /**
     * Allows to execute callback with a non existing user having given authorization resolver
     * Beware : Sudoer, here, won't have any id. So, called code shouldn't rely on existing interlocutor id
     * for its role checking
     * @param authorizationsResolver Fake authorization resolver to set to the sudoer
     * @param runnable The call we want to sudo
     */
    public void executeWithFakeRights(AuthenticatedUser.AuthorizationsResolver authorizationsResolver, Runnable runnable){
        AuthenticatedUser sudoer = new AuthenticatedUser(
                new UserIdentity(new Long(-1)).setEmail("fake@fakeuser.fr").setFirstName("Fake").setLastName("User")
        ).setGlobalRole(GlobalRole.ADMIN).setStoreRoles(new ArrayList<AuthenticatedUser.UserStoreRole>()).setLocale(Locale.FRANCE);
        sudoer.replaceAuthorizationResolverWith(authorizationsResolver);
        sudoExecute(sudoer, runnable);
    }
}
