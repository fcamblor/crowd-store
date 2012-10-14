package com.crowdstore.service.user;

import com.crowdstore.models.auth.Credentials;
import com.crowdstore.models.users.AuthenticatedUser;
import com.crowdstore.persistence.user.UserDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author fcamblor
 */
@Service
public class UserServiceImpl implements UserService {

    @Inject
    UserDao userDao;

    @Override
    public AuthenticatedUser authenticate(Credentials credentials) throws AuthenticationError {
        AuthenticatedUser authenticatedUser = userDao.findAuthenticatedUserByCredentials(credentials);
        if(authenticatedUser == null){
            throw new AuthenticationError("Bad credentials !");
        }
        return authenticatedUser;
    }
}
