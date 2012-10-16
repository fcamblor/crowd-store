package com.crowdstore.service.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.FlatUser;
import com.crowdstore.persistence.user.UserDao;
import com.crowdstore.service.security.PermissionsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

import static com.crowdstore.common.security.Permissions.ensure;

/**
 * @author fcamblor
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    @Inject
    PermissionsService permissionsService;

    @Override
    public AuthenticatedUser authenticate(Credentials credentials) throws AuthenticationError {
        AuthenticatedUser authenticatedUser = userDao.findAuthenticatedUserByCredentials(credentials);
        if(authenticatedUser == null){
            throw new AuthenticationError("Bad credentials !");
        }
        return authenticatedUser;
    }

    @Override
    public void createUser(FlatUser user) {
        ensure(permissionsService.canCreateNewUser(), "Cannot create new user");

        AuthenticatedUser existingUser = userDao.findAuthenticatedUserByPrivateToken(user.getPrivateToken());
        if(existingUser != null){
            throw new IllegalArgumentException("Given private token is not unique");
        }

        userDao.createUser(user);
    }

    @Override
    public void hardDeleteUsersByIds(Long... userIds) {
        ensure(permissionsService.canDeleteUsers(), "Cannot delete users");

        // Detaching entities linked to users
        userDao.detachStoresToUsers(userIds);

        userDao.hardDeleteUsersByIds(userIds);
    }
}
