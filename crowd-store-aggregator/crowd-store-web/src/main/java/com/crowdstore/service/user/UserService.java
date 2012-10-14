package com.crowdstore.service.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;

/**
 * @author fcamblor
 */
public interface UserService {
    AuthenticatedUser authenticate(Credentials credentials) throws AuthenticationError;
}
