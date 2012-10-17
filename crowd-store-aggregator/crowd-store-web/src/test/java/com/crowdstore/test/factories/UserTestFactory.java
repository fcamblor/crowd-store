package com.crowdstore.test.factories;

import com.crowdstore.models.common.IdentifiableContainer;
import com.crowdstore.models.role.GlobalRole;
import com.crowdstore.models.users.FlatUser;
import com.crowdstore.models.users.UserIdentity;
import com.crowdstore.restapi.common.PermissionFaker;
import com.crowdstore.service.user.UserService;
import com.crowdstore.test.factories.common.EntitiesTestFactory;
import com.google.common.collect.Collections2;
import com.google.common.hash.Hashing;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author fcamblor
 */
@Component
public class UserTestFactory extends EntitiesTestFactory {

    @Inject
    UserService userService;

    @Inject
    PermissionFaker permissionFaker;

    public FlatUser newPersistedFlatUserAdmin(String userKey) { return newPersistedFlatUser(userKey, GlobalRole.ADMIN); }
    public FlatUser newPersistedFlatUser(String userKey, GlobalRole globalRole) {
        final FlatUser user = new FlatUser(new UserIdentity(null).setEmail(userKey+"@unittesting.blah").setFirstName(userKey).setLastName(userKey))
                .setHashedPassword(Hashing.sha512().hashString(userKey+"_encodedPassword").toString())
                .setPrivateToken(userKey + "_privToken").setGlobalRole(globalRole);
        permissionFaker.executeWithAllRights(new Runnable() {
            @Override
            public void run() {
                userService.createUser(user);
            }
        });
        return user;
    }

    public void cleanPersistedFlatUsers(boolean rethrowException, FlatUser... createdFlatUsers){
        cleanPersistedFlatUsers(rethrowException, Arrays.asList(createdFlatUsers));
    }

    public void cleanPersistedFlatUsers(boolean rethrowException, final List<FlatUser> createdFlatUsers) {
        super.cleanPersistedElements(rethrowException, createdFlatUsers, new Runnable() {
                        @Override
                        public void run() {
                            Collection<Long> userIdsToDelete = Collections2.transform(createdFlatUsers, IdentifiableContainer.EXTRACT_ID_FCT);
                            userService.hardDeleteUsersByIds(userIdsToDelete.toArray(new Long[0]));
                        }
                    });
    }

}
