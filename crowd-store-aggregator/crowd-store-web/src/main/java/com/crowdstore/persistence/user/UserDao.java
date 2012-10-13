package com.crowdstore.persistence.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;

/**
 * @author damienriccio
 */
public interface UserDao {
    AuthenticatedUser findAuthenticatedUserByCredentials(Credentials credentials);
}
