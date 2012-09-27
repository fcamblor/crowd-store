package com.crowdstore.service.user;

import com.crowdstore.models.user.User;
import com.crowdstore.models.user.UserStoreRole;
import com.crowdstore.persistence.user.store.UserDao;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * @author damienriccio
 */
@Service
public class UserServiceImpl implements UserService {
    @Inject
    UserDao userDao;
}
