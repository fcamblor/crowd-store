package com.crowdstore.persistence.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.models.users.FlatUser;

/**
 * @author damienriccio
 */
public interface UserDao {
    AuthenticatedUser findAuthenticatedUserByCredentials(Credentials credentials);
    AuthenticatedUser findAuthenticatedUserByPrivateToken(String privateToken);
    void createUser(FlatUser user);
    void hardDeleteUsersByIds(Long... userIds);
    void detachStoresToUsers(Long[] userIds);
}
