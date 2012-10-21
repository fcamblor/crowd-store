package com.crowdstore.web.auth;

import com.crowdstore.models.security.GlobalAuthorization;
import com.crowdstore.models.security.StoreAuthorization;
import com.crowdstore.models.store.FlatStore;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.FlatUser;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.restapi.auth.AuthenticationRestService;
import com.crowdstore.restapi.common.RestSession;
import com.crowdstore.service.user.AuthenticationError;
import com.crowdstore.test.ApplicationContextAwareTest;
import com.crowdstore.test.rules.RequiresRunningEmbeddedTomcat;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author fcamblor
 */
public class AuthenticationControllerTest extends ApplicationContextAwareTest {

    @Inject
    AuthenticationRestService authenticationRestService;

    @Rule
    public RequiresRunningEmbeddedTomcat embeddedTomcat = new RequiresRunningEmbeddedTomcat();

    @Test
    public void shouldAuthenticationBeOk() throws AuthenticationError {
        final FlatUser WORKING_USER;
        final FlatStore store1, store2;
        MODELS_PREPARATION: {
            store1 = modelsTestFactory.newPersistedFlatStore("store1");
            store2 = modelsTestFactory.newPersistedFlatStore("store2");

            WORKING_USER = modelsTestFactory.newPersistedFlatUserAdmin(
                    "authTester",
                    new String[]{store1.getIdentity().getName(), store2.getIdentity().getName()}
            );
        }

        RestSession restSession;
        CODE_UNDER_TEST: {
            restSession = authenticationRestService.authenticate(WORKING_USER.getIdentity().getEmail(), WORKING_USER.getHashedPassword());
        }

        VERIFICATIONS: {
            assertThat(restSession, is(notNullValue()));

            AuthenticatedUser authenticatedUser = restSession.getAuthenticatedUser();
            assertThat(authenticatedUser, is(notNullValue()));
            assertThat(authenticatedUser.getGlobalAuthorizations(), containsInAnyOrder(GlobalAuthorization.values()));

            Map<String,List<StoreAuthorization>> storesAuthorizations = authenticatedUser.getStoresAuthorizations();
            assertThat(storesAuthorizations, hasKey(equalTo(store1.getIdentity().getName())));
            assertThat(storesAuthorizations, hasKey(equalTo(store2.getIdentity().getName())));

            UserIdentity authenticatedUserIdentity = authenticatedUser.getIdentity();
            assertThat(authenticatedUserIdentity.getEmail(), is(equalTo(WORKING_USER.getIdentity().getEmail())));
            assertThat(authenticatedUserIdentity.getFirstName(), is(equalTo(WORKING_USER.getIdentity().getFirstName())));
            assertThat(authenticatedUserIdentity.getLastName(), is(equalTo(WORKING_USER.getIdentity().getLastName())));
            // Really useful ?.. wondering...
            assertThat(authenticatedUserIdentity.getDisplayName(), is(equalTo(WORKING_USER.getIdentity().getDisplayName())));
        }
    }
}
