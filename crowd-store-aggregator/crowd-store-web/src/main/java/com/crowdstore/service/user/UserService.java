package com.crowdstore.service.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.FlatUser;

/**
 * @author fcamblor
 */
public interface UserService {
    AuthenticatedUser authenticate(Credentials credentials) throws AuthenticationError;
    void createUser(FlatUser user);
    void hardDeleteUsersByIds(Long... userIds);
}
